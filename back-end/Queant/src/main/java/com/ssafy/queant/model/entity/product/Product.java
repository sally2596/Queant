package com.ssafy.queant.model.entity.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private int bankId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String scodeId;

    @Column(nullable = false)
    private boolean isDeposit;

    private Integer ageMin;
    private Integer ageMax;
    private Long budgetMin;
    private Long budgetMax;
    private Integer termMin;
    private Integer termMax;
    private String etc;

    @Column(nullable = false)
    private boolean isEnabled;
}
