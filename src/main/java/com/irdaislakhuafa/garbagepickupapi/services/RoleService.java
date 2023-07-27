package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Role;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.role.RoleRequest;
import com.irdaislakhuafa.garbagepickupapi.services.converter.CRUDService;
import com.irdaislakhuafa.garbagepickupapi.services.converter.EntityConverterService;

import java.util.List;

public interface RoleService extends CRUDService<Role, String>, EntityConverterService<Role, RoleRequest, Object> {

    List<Role> findAll();

}