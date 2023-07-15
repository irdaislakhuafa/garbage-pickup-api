package com.irdaislakhuafa.garbagepickupapi.services;

import java.util.Optional;

import com.irdaislakhuafa.garbagepickupapi.models.gql.request.RoleRequest;

public interface RoleService<R> {
	Optional<R> save(R request);

	R fromRequestToEntity(RoleRequest request);
}
