package com.ssafy.queant.model.entity.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Conditions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int conditionId;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private String scodeId;

    @Column(nullable = false)
    private float specialRate;

    private String conditionInfo;
}
