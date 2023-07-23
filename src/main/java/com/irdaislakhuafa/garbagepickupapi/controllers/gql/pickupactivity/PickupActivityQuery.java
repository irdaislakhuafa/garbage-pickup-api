package com.irdaislakhuafa.garbagepickupapi.controllers.gql.pickupactivity;

import com.irdaislakhuafa.garbagepickupapi.models.entities.PickupActivity;
import com.irdaislakhuafa.garbagepickupapi.services.PickupActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "PickupActivityQuery")
public class PickupActivityQuery {
    private final PickupActivityService pickupActivityService;

    @SchemaMapping
    public List<PickupActivity> findAllByPickupId(@Argument(name = "pickupId") String pickupId) {
        final var results = this.pickupActivityService.findAllByPickupId(pickupId);
        return results;
    }

    @SchemaMapping
    public List<PickupActivity> findAllByPickupResi(@Argument(name = "pickupResi") String pickupResi) {
        final var results = this.pickupActivityService.findAllByPickupResi(pickupResi);
        return results;
    }
}