package com.irdaislakhuafa.garbagepickupapi.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserUpdateRequest;

public interface UserService<U> extends UserDetailsService {
    Optional<U> save(U request);

    Optional<U> update(U request);

    U fromRequestToEntity(UserRequest request);

    Optional<U> getCurrentUser();

    U fromUpdateRequestToEntity(UserUpdateRequest request);
}