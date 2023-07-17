package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.DataAlreadyExists;
import com.irdaislakhuafa.garbagepickupapi.models.entities.Pickup;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.PickupRepository;
import com.irdaislakhuafa.garbagepickupapi.services.PickupService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PickupServiceImpl implements PickupService {
    private final PickupRepository pickupRepository;

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
        return null;
    }

    @Override
    public Pickup fromUpdateRequestToEntity(Object request) {
        return null;
    }
}