package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.user.UserRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.user.UserUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.services.converter.CRUDService;
import com.irdaislakhuafa.garbagepickupapi.services.converter.EntityConverterService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService, EntityConverterService<User, UserRequest, UserUpdateRequest>, CRUDService<User, String> {
    /**
     * @param request will save or create new user with encoded password (encoded with BCryptPasswordEncoder)
     */
    Optional<User> save(User request);


    /**
     * @param request not update some field {password, createdAt, createdBy, deletedAt, deletedBy}
     */
    Optional<User> update(User request);


    User getCurrentUser();

    List<User> findAll();
}