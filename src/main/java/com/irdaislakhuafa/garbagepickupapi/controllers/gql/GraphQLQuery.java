package com.irdaislakhuafa.garbagepickupapi.controllers.gql;

import com.irdaislakhuafa.garbagepickupapi.controllers.gql.hello.HelloQuery;
import com.irdaislakhuafa.garbagepickupapi.controllers.gql.pickup.PickupQuery;
import com.irdaislakhuafa.garbagepickupapi.controllers.gql.trashtype.TrashTypeQuery;
import com.irdaislakhuafa.garbagepickupapi.controllers.gql.user.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "Query")
@RequiredArgsConstructor
public class GraphQLQuery {
    private final HelloQuery hello;
    private final UserQuery user;
    private final TrashTypeQuery trashType;
    private final PickupQuery pickup;

    @SchemaMapping(field = "hello")
    public HelloQuery helloQuery() {
        return this.hello;
    }

    @SchemaMapping(field = "user")
    public UserQuery userQuery() {
        return this.user;
    }

    @SchemaMapping(field = "trashType")
    public TrashTypeQuery trashType() {
        return this.trashType;
    }

    @SchemaMapping(field = "pickup")
    public PickupQuery pickup() {
        return this.pickup;
    }
}