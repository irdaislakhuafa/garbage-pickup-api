package com.irdaislakhuafa.garbagepickupapi.controllers.gql;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.irdaislakhuafa.garbagepickupapi.controllers.gql.role.RoleMutation;
import com.irdaislakhuafa.garbagepickupapi.controllers.gql.user.UserMutation;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "Mutation")
@RequiredArgsConstructor
public class GraphQLMutation {
	private final UserMutation user;
	private final RoleMutation role;

	@SchemaMapping(field = "user")
	public UserMutation user() {
		return this.user;
	}

	@SchemaMapping(field = "role")
	public RoleMutation role() {
		return this.role;
	}

}
