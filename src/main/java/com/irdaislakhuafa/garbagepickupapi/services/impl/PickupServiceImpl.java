package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.DataAlreadyExists;
import com.irdaislakhuafa.garbagepickupapi.helpers.DistanceCalculatorHelper;
import com.irdaislakhuafa.garbagepickupapi.models.entities.Pickup;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.PickupRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.TrashTypeRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.services.PickupService;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PickupServiceImpl implements PickupService {
    private final PickupRepository pickupRepository;
    private final UserRepository userRepository;
    private final TrashTypeRepository trashTypeRepository;
    private final UserService userService;
    private final DistanceCalculatorHelper distanceCalculatorHelper;

    @Value(value = "${app.resi.prefix}")
    private String appResiPrefix;

    @Override
    public Optional<Pickup> save(Pickup request) {
        try {
            final var result = this.pickupRepository.save(request);
            return Optional.of(result);
        } catch (DataIntegrityViolationException e) {
            throw new DataAlreadyExists("this request pickup already exists");
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Set<Pickup> findAllByUserId(String userId) {
        return null;
    }

    @Override
    public Pickup fromRequestToEntity(PickupRequest request) {
        final var user = this.userRepository.findById(request.getUserId());
        if (user.isEmpty()) {
            throw new BadRequestException(String.format("user with id '%s' not found", request.getUserId()));
        }

        final var trashType = this.trashTypeRepository.findById(request.getTrashTypeId());
        if (trashType.isEmpty()) {
            throw new BadRequestException(String.format("trash type with id '%s' not found", request.getTrashTypeId()));
        }

//        TODO: added logic how to assign pickup to courier
//        this.userRepository.find

        final var result = Pickup.builder()
                .resi(this.appResiPrefix)
                .user(user.get())
//                .courier()
                .status(request.getStatus())
                .weight(request.getWeight())
                .place(request.getPlace())
                .trashType(trashType.get())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .createdBy(this.userService.getCurrentUser().getId())
                .build();

        return null;
    }

    @Override
    public Pickup fromUpdateRequestToEntity(Object request) {
        return null;
    }
}