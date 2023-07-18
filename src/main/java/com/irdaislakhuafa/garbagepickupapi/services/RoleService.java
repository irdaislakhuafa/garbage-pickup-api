package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Role;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.role.RoleRequest;
import com.irdaislakhuafa.garbagepickupapi.services.converter.EntityConverterService;

import java.util.Optional;

public interface RoleService extends EntityConverterService<Role, RoleRequest, Object> {
    Optional<Role> save(Role request);

}