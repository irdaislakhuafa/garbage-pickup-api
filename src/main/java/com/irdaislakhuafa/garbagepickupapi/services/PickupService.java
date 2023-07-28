package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Pickup;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupCheckPriceRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupFindAllByUserIdWithRangeDateRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.response.PickupCheckPriceResponse;
import com.irdaislakhuafa.garbagepickupapi.services.converter.CRUDService;
import com.irdaislakhuafa.garbagepickupapi.services.converter.EntityConverterService;

import java.util.Optional;
import java.util.Set;

public interface PickupService extends EntityConverterService<Pickup, PickupRequest, PickupUpdateRequest>, CRUDService<Pickup, String> {

    /**
     * this method doesn't update fields [resi]
     */
    Optional<Pickup> update(Pickup request);

    /**
     * this method is used to retrieve all the list of pickup requests that have been made by the user
     *
     * @param userId this method will fetch a list of pickup requests based on the user with this id
     * @param start  is string date time value with format "dd/MM/YYYY HH:MM:ss"
     * @param end    is string date time value with format "dd/MM/YYYY HH:MM:ss"
     */
    Set<Pickup> findAllByUserIdWithRange(PickupFindAllByUserIdWithRangeDateRequest request);

    PickupCheckPriceResponse checkPrice(PickupCheckPriceRequest request);
}