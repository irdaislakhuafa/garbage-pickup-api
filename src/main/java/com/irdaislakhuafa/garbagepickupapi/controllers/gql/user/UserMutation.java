package com.irdaislakhuafa.garbagepickupapi.controllers.gql.user;

import java.util.Optional;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserRequest;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@SchemaMapping(typeName = "UserMutation")
@RequiredArgsConstructor
@Slf4j
public class UserMutation {
	private final UserService userService;

	@SchemaMapping
	public Optional<User> save(@Argument(name = "request") UserRequest request) {
		var user = this.userService.fromRequestToEntity(request);
		var result = this.userService.save(user);
		return result;
	}
}
