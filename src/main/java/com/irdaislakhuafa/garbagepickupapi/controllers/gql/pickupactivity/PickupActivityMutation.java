package com.irdaislakhuafa.garbagepickupapi.controllers.gql.pickupactivity;

import com.irdaislakhuafa.garbagepickupapi.models.entities.PickupActivity;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickupactivity.PickupActivityRequest;
import com.irdaislakhuafa.garbagepickupapi.services.PickupActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "PickupActivityMutation")
public class PickupActivityMutation {
    private final PickupActivityService pickupActivityService;

    @SchemaMapping
    public Optional<PickupActivity> save(@Argument(name = "request") PickupActivityRequest request) {
        final var pickupActivity = this.pickupActivityService.fromRequestToEntity(request);
        final var result = this.pickupActivityService.save(pickupActivity);
        return result;
    }
}