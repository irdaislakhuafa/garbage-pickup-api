package com.irdaislakhuafa.garbagepickupapi.models.gql.request;

import java.util.ArrayList;

import com.irdaislakhuafa.garbagepickupapi.models.gql.request.utils.UserRoles;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserRequest {
	private String name;
	private String email;
	private String password;
	private String image;
	private String phone;
	private String address;
	@Builder.Default
	private int saldo = 0;
	@Builder.Default
	private int point = 0;
	@Builder.Default
	private ArrayList<UserRoles> roles = new ArrayList<>();
}
