package com.irdaislakhuafa.garbagepickupapi.models.gql.request;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserRequest {
	private String name;
	private String email;
	private String password;
	private String image;
	private String phone;
	private int saldo = 0;
	private int point = 0;
}
