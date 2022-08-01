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
public class Options {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int optionId;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private float baseRate;

    private Float highBaseRate;

    @Column(nullable = false)
    private int saveTerm;

    @Column(nullable = false)
    private boolean rateType; // 단리 복리

    private Boolean rsrvType; // 자유 적립식, 정액 적립식
}
