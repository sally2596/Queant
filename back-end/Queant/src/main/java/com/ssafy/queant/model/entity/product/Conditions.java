package com.ssafy.queant.model.entity.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
//@Entity
//@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Conditions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int condition_id;

    @Column(nullable = false)
    private String product_id;

    @Column(nullable = false)
    private char code_id;

    @Column(nullable = false)
    private int scode_id;

    @Column(nullable = false)
    private float special_rate;

    @Column(nullable = false)
    private String condition_info;
}
