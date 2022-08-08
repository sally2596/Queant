package com.ssafy.queant.model.entity.portfolio;

import com.ssafy.queant.model.entity.product.Conditions;
import com.ssafy.queant.model.entity.product.Product;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int portfolioConditionId;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "portfolioId")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "conditionId")
    private Conditions conditions;
}
