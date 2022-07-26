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
    private int condition_id;

    @Column(nullable = false)
    private String product_id;

    @Column(nullable = false)
    private char code_id;

    @Column(nullable = false)
    private int scode_id;

    @Column(nullable = false)
    private Long special_rate;

    @Column(nullable = false)
    private String condition_info;
}
