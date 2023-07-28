package com.irdaislakhuafa.garbagepickupapi.controllers.gql.pickup;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Pickup;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupFindAllByUserIdWithRangeDateRequest;
import com.irdaislakhuafa.garbagepickupapi.services.PickupService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "PickupQuery")
public class PickupQuery {
    private final PickupService pickupService;

    @SchemaMapping
    public Set<Pickup> findAllByUserIdWithRangeDate(@Argument(name = "request") PickupFindAllByUserIdWithRangeDateRequest request) {
        final var results = this.pickupService.findAllByUserIdWithRange(request);
        return results;
    }
}