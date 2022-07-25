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
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int option_id;
    @Column(nullable = false)
    private String product_id;
    @Column(nullable = false)
    private Long base_rate;
    @Column(nullable = false)
    private Long high_base_rate;
    @Column(nullable = false)
    private int save_term;
    @Column(nullable = false)
    private boolean is_compound;
    private boolean is_fixed;
}
