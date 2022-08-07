package com.ssafy.queant.model.entity.portfolio;

import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.member.MemberRole;
import com.ssafy.queant.model.entity.product.Conditions;
import com.ssafy.queant.model.entity.product.Options;
import com.ssafy.queant.model.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Portfolio implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int portfolioId;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "optionId")
    private Options options;

    @OneToMany(mappedBy = "portfolio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<PortfolioCondition> portfolioConditions = new ArrayList<>();

    @Column(nullable = false)
    private int portfolioNo;
    @Column(nullable = false)
    private Long amount;
    @Column(nullable = false)
    private Date startDate;
    private Date endDate;
    private Long amountFixed;

    public void addPortfolioCondition(PortfolioCondition portfolioCondition){
        portfolioConditions.add(portfolioCondition);
    }
}
