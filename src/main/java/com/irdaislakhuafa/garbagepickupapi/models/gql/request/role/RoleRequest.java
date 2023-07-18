package com.irdaislakhuafa.garbagepickupapi.models.gql.request.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RoleRequest {
    private String name;
    private String description;
}