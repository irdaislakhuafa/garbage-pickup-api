package com.irdaislakhuafa.garbagepickupapi.models.gql.request.trashtype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TrashTypeUpdateRequest {
    private String id;
    private String name;
    private String description;
    private boolean isDeleted;
}