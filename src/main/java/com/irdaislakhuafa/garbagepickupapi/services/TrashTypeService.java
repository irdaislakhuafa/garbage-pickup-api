package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.entities.TrashType;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.trashtype.TrashTypeRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.trashtype.TrashTypeUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.services.converter.CRUDService;
import com.irdaislakhuafa.garbagepickupapi.services.converter.EntityConverterService;

import java.util.List;

public interface TrashTypeService extends EntityConverterService<TrashType, TrashTypeRequest, TrashTypeUpdateRequest>, CRUDService<TrashType, String> {
    List<TrashType> findAll();
}