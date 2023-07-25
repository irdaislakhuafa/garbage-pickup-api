package com.irdaislakhuafa.garbagepickupapi.models.entities;

import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.ReceiptStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Receipt extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ReceiptStatus status;

    @Column(nullable = false)
    @Builder.Default
    private int point = 0;

    @OneToOne
    @JoinColumn(nullable = false)
    private Pickup pickup;
}