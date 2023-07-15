package com.irdaislakhuafa.garbagepickupapi.controllers.gql.role;

import java.util.Optional;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Role;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.RoleRequest;
import com.irdaislakhuafa.garbagepickupapi.services.RoleService;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "RoleMutation")
@RequiredArgsConstructor
public class RoleMutation {
	private final RoleService<Role> roleService;

	@SchemaMapping
	public Optional<Role> save(@Argument(name = "request") RoleRequest request) {
		final var role = this.roleService.fromRequestToEntity(request);
		final var result = this.roleService.save(role);
		return result;
	}

}
