package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserUpdateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService<U> extends UserDetailsService {
    /**
     * @param request will save or create new user with encoded password (encoded with BCryptPasswordEncoder)
     */
    Optional<U> save(U request);


    /**
     * @param request not update some field {password, createdAt, createdBy, deletedAt, deletedBy}
     */
    Optional<U> update(U request);

    U fromRequestToEntity(UserRequest request);

    U getCurrentUser();

    U fromUpdateRequestToEntity(UserUpdateRequest request);
}