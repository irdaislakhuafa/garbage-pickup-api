package com.irdaislakhuafa.garbagepickupapi.services.converter;

import java.util.Optional;

public interface CRUDService<E, ID> {
    Optional<E> save(E request);

    Optional<E> update(E request);

    Optional<E> delete(ID id);

    Optional<E> findById(ID id);
}