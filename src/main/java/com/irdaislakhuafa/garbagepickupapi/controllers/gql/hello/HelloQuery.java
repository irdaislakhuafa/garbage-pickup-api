package com.irdaislakhuafa.garbagepickupapi.controllers.gql.hello;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "HelloQuery")
public class HelloQuery {
    @SchemaMapping(field = "sayHello")
    public String sayHello(@Argument(name = "message") String message) {
        return "Hello, " + message;
    }
}