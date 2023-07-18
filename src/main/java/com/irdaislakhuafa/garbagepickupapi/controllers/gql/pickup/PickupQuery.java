package com.irdaislakhuafa.garbagepickupapi.controllers.gql.pickup;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Pickup;
import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.PickupStatus;
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
    public Set<Pickup> findAllByUserIdWithRangeDate(
            @Argument(name = "userId") String userId,
            @Argument(name = "start") String start,
            @Argument(name = "end") String end,
            @Argument(name = "status") PickupStatus status) {
        final var results = this.pickupService.findAllByUserIdWithRange(userId, start, end, status);
        return results;
    }
}