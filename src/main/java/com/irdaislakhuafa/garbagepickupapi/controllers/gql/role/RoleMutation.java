package com.irdaislakhuafa.garbagepickupapi.controllers.gql.role;

import java.util.Optional;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Role;
import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.RoleRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserRequest;
import com.irdaislakhuafa.garbagepickupapi.services.RoleService;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@SchemaMapping(typeName = "RoleMutation")
@RequiredArgsConstructor
@Slf4j
public class RoleMutation {
	private final RoleService<Role> roleService;
	private final UserService<User, UserRequest> userService;

	@SchemaMapping
	public Optional<Role> save(@Argument(name = "request") RoleRequest request) {
		final var role = this.roleService.fromRequestToEntity(request);
		final var result = this.roleService.save(role);

		log.info("current user:" + this.userService.getCurrentUser());

		return result;
	}

}
