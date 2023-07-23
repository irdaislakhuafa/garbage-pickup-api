package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Pickup;
import com.irdaislakhuafa.garbagepickupapi.services.PickupActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PickupActivityServiceImpl implements PickupActivityService {

    @Override
    public Optional<Pickup> save(Pickup request) {
        return Optional.empty();
    }

    @Override
    public Optional<Pickup> update(Pickup request) {
        return Optional.empty();
    }

    @Override
    public Optional<Pickup> delete(String s) {
        return Optional.empty();
    }

    @Override
    public Optional<Pickup> findById(String s) {
        return Optional.empty();
    }

    @Override
    public List<Pickup> findAll() {
        return null;
    }
}