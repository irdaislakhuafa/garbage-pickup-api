package com.irdaislakhuafa.garbagepickupapi.models.gql.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RoleRequest {
	private String name;
	private String description;
}
