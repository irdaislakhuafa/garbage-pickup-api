package com.irdaislakhuafa.garbagepickupapi.controllers.gql.uservoucher;

import com.irdaislakhuafa.garbagepickupapi.models.entities.UserVoucher;
import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.UserVoucherStatus;
import com.irdaislakhuafa.garbagepickupapi.services.UserVoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@SchemaMapping(typeName = "UserVoucherQuery")
@RequiredArgsConstructor
public class UserVoucherQuery {
    private final UserVoucherService userVoucherService;

    @SchemaMapping(field = "findAllByUserIdAndStatus")
    public List<UserVoucher> findAll(
            @Argument(name = "userId") String userId,
            @Argument(name = "status") List<UserVoucherStatus> status) {
        final var results = this.userVoucherService.findAll(userId, status);
        return results;
    }
}