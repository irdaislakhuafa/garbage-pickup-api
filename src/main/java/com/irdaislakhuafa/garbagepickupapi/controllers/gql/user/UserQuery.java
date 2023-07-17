package com.irdaislakhuafa.garbagepickupapi.controllers.gql.user;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@SchemaMapping(typeName = "UserQuery")
@RequiredArgsConstructor
public class UserQuery {
    private final UserService userService;

    @SchemaMapping
    public Optional<User> findById(@Argument(name = "id") String id) {
        final var result = this.userService.findById(id);
        return Optional.of(result);
    }
}