package com.irdaislakhuafa.garbagepickupapi.models.gql.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {
    private String email;
    private String password;

    public enum UserRoles {
        USER, COURIER, DEVELOPER
    }
}