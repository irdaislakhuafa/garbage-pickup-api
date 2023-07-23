package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.models.entities.PickupActivity;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickupactivity.PickupActivityRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickupactivity.PickupActivityUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.PickupActivityRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.PickupRepository;
import com.irdaislakhuafa.garbagepickupapi.services.PickupActivityService;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PickupActivityServiceImpl implements PickupActivityService {
    private final UserService userService;
    private final PickupActivityRepository pickupActivityRepository;
    private final PickupRepository pickupRepository;

    @Override
    public Optional<PickupActivity> save(PickupActivity request) {
        try {
            request.setCreatedAt(LocalDateTime.now());
            request.setCreatedBy(this.userService.getCurrentUser().getId());

            final var result = this.pickupActivityRepository.save(request);
            return Optional.of(result);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Optional<PickupActivity> update(PickupActivity request) {
        return Optional.empty();
    }

    @Override
    public Optional<PickupActivity> delete(String s) {
        return Optional.empty();
    }

    @Override
    public Optional<PickupActivity> findById(String s) {
        return Optional.empty();
    }

    @Override
    public List<PickupActivity> findAll() {
        return null;
    }

    @Override
    public PickupActivity fromRequestToEntity(PickupActivityRequest request) {
        final var pickup = this.pickupRepository.findById(request.getPickupId());
        if (pickup.isEmpty()) {
            throw new BadRequestException(String.format("pickup with id '%s' not found", request.getPickupId()));
        }

        final var result = PickupActivity.builder()
                .pickup(pickup.get())
                .description(request.getDescription())
                .build();

        return result;
    }

    @Override
    public PickupActivity fromUpdateRequestToEntity(PickupActivityUpdateRequest request) {
        return null;
    }
}