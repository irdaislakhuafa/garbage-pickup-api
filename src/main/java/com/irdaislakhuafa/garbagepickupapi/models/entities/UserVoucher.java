package com.irdaislakhuafa.garbagepickupapi.models.entities;

import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.UserVoucherStatus;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "user_voucher")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserVoucher extends BaseEntity {
	private User user;
	private Voucher voucher;
	private UserVoucherStatus status;
}
