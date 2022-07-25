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
    private String product_id;
    @Column(nullable = false)
    private Long bank_id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean is_deposit;
    private int age_min;
    private int age_max;
    private Long budget_min;
    private Long budget_max;
    private int term_min;
    private int term_max;
    private String etc;

}
