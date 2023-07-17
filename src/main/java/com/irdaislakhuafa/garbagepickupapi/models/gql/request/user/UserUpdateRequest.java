package com.irdaislakhuafa.garbagepickupapi.models.gql.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserUpdateRequest {
    private String id;
    private String name;
    private String email;
    private String image;
    private String phone;
    private String address;
    @Builder.Default
    private int saldo = 0;
    @Builder.Default
    private int point = 0;
    @Builder.Default
    private ArrayList<UserLoginRequest.UserRoles> roles = new ArrayList<>();
    private boolean isDeleted;
}