package com.irdaislakhuafa.garbagepickupapi.controllers.gql.role;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Role;
import com.irdaislakhuafa.garbagepickupapi.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "RoleQuery")
public class RoleQuery {
    private final RoleService roleService;

    @SchemaMapping
    public List<Role> findAll() {
        final var results = this.roleService.findAll();
        return results;
    }

    @SchemaMapping
    public Optional<Role> findById(@Argument(name = "id") String id) {
        final var result = this.roleService.findById(id);
        return result;
    }
}