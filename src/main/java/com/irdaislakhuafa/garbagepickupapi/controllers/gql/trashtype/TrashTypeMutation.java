package com.irdaislakhuafa.garbagepickupapi.controllers.gql.trashtype;

import com.irdaislakhuafa.garbagepickupapi.models.entities.TrashType;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.trashtype.TrashTypeRequest;
import com.irdaislakhuafa.garbagepickupapi.services.TrashTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "TrashTypeMutation")
public class TrashTypeMutation {
    private final TrashTypeService trashTypeService;

    @SchemaMapping
    public Optional<TrashType> save(@Argument(name = "request") TrashTypeRequest request) {
        final var trashType = this.trashTypeService.fromRequestToEntity(request);
        final var result = this.trashTypeService.save(trashType);
        return result;
    }
}