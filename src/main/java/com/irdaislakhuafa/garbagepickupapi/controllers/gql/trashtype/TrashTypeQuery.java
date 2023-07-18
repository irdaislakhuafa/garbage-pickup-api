package com.irdaislakhuafa.garbagepickupapi.controllers.gql.trashtype;

import com.irdaislakhuafa.garbagepickupapi.models.entities.TrashType;
import com.irdaislakhuafa.garbagepickupapi.services.TrashTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "TrashTypeQuery")
@Slf4j
public class TrashTypeQuery {
    private final TrashTypeService trashTypeService;

    @SchemaMapping
    public List<TrashType> findAll() {
        final var result = this.trashTypeService.findAll();
        return result;
    }

    @SchemaMapping
    public Optional<TrashType> findById(@Argument(name = "id") String id) {
        final var result = this.trashTypeService.findById(id);
        return result;
    }
}