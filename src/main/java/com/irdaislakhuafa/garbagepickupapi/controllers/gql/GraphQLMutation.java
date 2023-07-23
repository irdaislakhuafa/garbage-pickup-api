package com.irdaislakhuafa.garbagepickupapi.controllers.gql;

import com.irdaislakhuafa.garbagepickupapi.controllers.gql.pickup.PickupMutation;
import com.irdaislakhuafa.garbagepickupapi.controllers.gql.pickupactivity.PickupActivityMutation;
import com.irdaislakhuafa.garbagepickupapi.controllers.gql.role.RoleMutation;
import com.irdaislakhuafa.garbagepickupapi.controllers.gql.trashtype.TrashTypeMutation;
import com.irdaislakhuafa.garbagepickupapi.controllers.gql.user.UserMutation;
import com.irdaislakhuafa.garbagepickupapi.controllers.gql.uservoucher.UserVoucherMutation;
import com.irdaislakhuafa.garbagepickupapi.controllers.gql.voucher.VoucherMutation;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "Mutation")
@RequiredArgsConstructor
public class GraphQLMutation {
    private final UserMutation user;
    private final RoleMutation role;
    private final PickupMutation pickup;
    private final TrashTypeMutation trashType;
    private final VoucherMutation voucher;
    private final UserVoucherMutation userVoucher;
    private final PickupActivityMutation pickupActivity;

    @SchemaMapping(field = "user")
    public UserMutation user() {
        return this.user;
    }

    @SchemaMapping(field = "role")
    public RoleMutation role() {
        return this.role;
    }

    @SchemaMapping(field = "pickup")
    public PickupMutation pickup() {
        return this.pickup;
    }

    @SchemaMapping(field = "trashType")
    public TrashTypeMutation trashType() {
        return this.trashType;
    }

    @SchemaMapping(field = "voucher")
    public VoucherMutation voucher() {
        return this.voucher;
    }

    @SchemaMapping(field = "userVoucher")
    public UserVoucherMutation userVoucher() {
        return this.userVoucher;
    }

    @SchemaMapping(field = "pickupActivity")
    public PickupActivityMutation pickupActivity() {
        return this.pickupActivity;
    }
}