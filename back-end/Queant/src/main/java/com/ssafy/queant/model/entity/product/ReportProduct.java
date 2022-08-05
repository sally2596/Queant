package com.ssafy.queant.model.entity.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportProductId;

    @Column(nullable = false)
    private UUID member_id;

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String referenceData;

    @Column(nullable = false)
    private boolean isDeposit;

    @Column(nullable = false)
    private boolean isUpdated;

    private String etc;
}
