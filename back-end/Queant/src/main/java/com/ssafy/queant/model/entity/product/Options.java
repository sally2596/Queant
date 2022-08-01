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
public class Options {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int option_id;

    @Column(nullable = false)
    private String product_id;

    @Column(nullable = false)
    private float base_rate;

    @Column(nullable = false)
    private float high_base_rate;

    @Column(nullable = false)
    private int save_term;

    @Column(nullable = false)
    private boolean is_compound;

    private boolean is_fixed;
}
