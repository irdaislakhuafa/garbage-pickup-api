package com.irdaislakhuafa.garbagepickupapi.controllers.gql.user;

import java.util.Optional;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserRequest;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "UserMutation")
@RequiredArgsConstructor
public class UserMutation {
	private final UserService userService;

	@SchemaMapping
	public Optional<User> save(@Argument("request") UserRequest request) {
		return Optional.ofNullable(User.builder().build());
	}
}
