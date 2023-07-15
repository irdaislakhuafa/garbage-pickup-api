package com.irdaislakhuafa.garbagepickupapi.models.gql.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {
	private String email;
	private String password;
}
