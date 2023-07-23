package com.irdaislakhuafa.garbagepickupapi.controllers.gql.pickup;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Pickup;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.response.PickupCheckPriceResponse;
import com.irdaislakhuafa.garbagepickupapi.services.PickupService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "PickupMutation")
public class PickupMutation {
    private final PickupService pickupService;

    @SchemaMapping
    public Optional<Pickup> save(@Argument(name = "request") PickupRequest request) {
        final var pickup = this.pickupService.fromRequestToEntity(request);
        final var result = this.pickupService.save(pickup);
        return result;
    }

    @SchemaMapping
    public Optional<Pickup> update(@Argument(name = "request") PickupUpdateRequest request) {
        final var pickup = this.pickupService.fromUpdateRequestToEntity(request);
        final var result = this.pickupService.update(pickup);
        return result;
    }

    @SchemaMapping
    public PickupCheckPriceResponse checkPrice() {
        final var result = this.pickupService.checkPrice(0, 0, 0);
        return result;
    }
}