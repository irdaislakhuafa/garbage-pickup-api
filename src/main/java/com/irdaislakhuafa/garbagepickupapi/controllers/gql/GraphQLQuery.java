package com.irdaislakhuafa.garbagepickupapi.controllers.gql;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.irdaislakhuafa.garbagepickupapi.controllers.gql.hello.HelloQuery;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "Query")
@RequiredArgsConstructor
public class GraphQLQuery {
	private final HelloQuery hello;

	@SchemaMapping(field = "hello")
	public HelloQuery helloQuery() {
		return this.hello;
	}
}
