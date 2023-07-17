package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Pickup;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupRequest;
import com.irdaislakhuafa.garbagepickupapi.services.converter.EntityConverterService;

import java.util.Optional;
import java.util.Set;

public interface PickupService extends EntityConverterService<Pickup, PickupRequest, Object> {
    Optional<Pickup> save(Pickup request);

    /**
     * this method is used to retrieve all the list of pickup requests that have been made by the user
     *
     * @param userId this method will fetch a list of pickup requests based on the user with this id
     */
    Set<Pickup> findAllByUserId(String userId);
}