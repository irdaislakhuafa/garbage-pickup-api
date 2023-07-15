package com.irdaislakhuafa.garbagepickupapi.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService<U, R> extends UserDetailsService {
    Optional<U> save(U user);

    U fromRequestToEntity(R request);
}