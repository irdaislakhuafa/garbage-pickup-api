package com.irdaislakhuafa.garbagepickupapi.models.gql.request.trashtype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TrashTypeRequest {
    private String name;
    private String description;
}