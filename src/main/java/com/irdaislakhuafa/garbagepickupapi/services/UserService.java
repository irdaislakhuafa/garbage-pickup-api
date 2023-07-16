package com.irdaislakhuafa.garbagepickupapi.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService<U, R> extends UserDetailsService {
    Optional<U> save(U user);

    Optional<U> update(U user);

    U fromRequestToEntity(R request);

    Optional<U> getCurrentUser();
}