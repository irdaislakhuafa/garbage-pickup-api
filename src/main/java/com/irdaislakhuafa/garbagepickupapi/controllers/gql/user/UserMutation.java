package com.irdaislakhuafa.garbagepickupapi.controllers.gql.user;

import java.util.Optional;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;

@Controller
@SchemaMapping(typeName = "UserMutation")
public class UserMutation {
	@SchemaMapping
	public Optional<User> save(@Argument("request") User user) {
		return Optional.ofNullable(user);
	}
}
