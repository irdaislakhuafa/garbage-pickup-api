package com.irdaislakhuafa.garbagepickupapi.models.entities;

import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.UserVoucherStatus;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "user_voucher")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserVoucher extends BaseEntity {
	@JoinColumn(nullable = false)
	@ManyToOne
	private User user;

	@JoinColumn(nullable = false)
	@ManyToOne
	private Voucher voucher;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UserVoucherStatus status;
}
