package com.irdaislakhuafa.garbagepickupapi.controllers.gql.uservoucher;

import com.irdaislakhuafa.garbagepickupapi.models.entities.UserVoucher;
import com.irdaislakhuafa.garbagepickupapi.services.UserVoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "UserVoucherMutation")
@Slf4j
public class UserVoucherMutation {
    private final UserVoucherService userVoucherService;

    @SchemaMapping
    public List<UserVoucher> exchange(@Argument(name = "userId") String userId, @Argument(name = "listId") List<String> listId) {
        final var result = this.userVoucherService.exchange(userId, listId);
        return result;
    }
}