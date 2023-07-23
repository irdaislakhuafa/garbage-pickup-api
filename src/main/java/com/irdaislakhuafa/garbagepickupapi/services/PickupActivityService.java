package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.entities.PickupActivity;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickupactivity.PickupActivityRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickupactivity.PickupActivityUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.services.converter.CRUDService;
import com.irdaislakhuafa.garbagepickupapi.services.converter.EntityConverterService;

public interface PickupActivityService extends CRUDService<PickupActivity, String>, EntityConverterService<PickupActivity, PickupActivityRequest, PickupActivityUpdateRequest> {
}