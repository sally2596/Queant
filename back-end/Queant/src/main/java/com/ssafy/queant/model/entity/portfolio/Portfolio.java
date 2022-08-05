package com.ssafy.queant.model.entity.portfolio;

import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int portfolioId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Column(nullable = false)
    private int portfolioNo;
    @Column(nullable = false)
    private Long amount;
    @Column(nullable = false)
    private Date startDate;
    private Date endDate;
    @Column(nullable = false)
    @Builder.Default
    private float specialRate = 0f;
    private Long amountFixed;
}
