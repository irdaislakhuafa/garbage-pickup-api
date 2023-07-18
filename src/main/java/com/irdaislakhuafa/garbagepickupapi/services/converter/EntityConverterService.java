package com.irdaislakhuafa.garbagepickupapi.services.converter;

public interface EntityConverterService<E, S, U> {

    E fromRequestToEntity(S request);


    E fromUpdateRequestToEntity(U request);
}