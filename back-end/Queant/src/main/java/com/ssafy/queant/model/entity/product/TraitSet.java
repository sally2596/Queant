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
public class TraitSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int traitId;

    @Column(nullable = false)
    private int productId;

    @Column(nullable = false)
    private String scodeId;
}
