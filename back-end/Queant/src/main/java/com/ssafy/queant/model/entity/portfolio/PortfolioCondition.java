package com.ssafy.queant.model.entity.portfolio;

import com.ssafy.queant.model.entity.product.Conditions;
import com.ssafy.queant.model.entity.product.Product;
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
public class PortfolioCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int portfolioConditionId;

    @ManyToOne
    @JoinColumn(name = "portfolioId")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "conditionId")
    private Conditions conditions;
}
