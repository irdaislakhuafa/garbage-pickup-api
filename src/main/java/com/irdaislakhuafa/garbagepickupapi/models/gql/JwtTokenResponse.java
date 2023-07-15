package com.irdaislakhuafa.garbagepickupapi.models.gql;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenResponse {
	private String message;
	private String token;
}
