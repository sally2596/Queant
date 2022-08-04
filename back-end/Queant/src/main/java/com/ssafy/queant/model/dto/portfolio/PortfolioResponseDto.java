package com.ssafy.queant.model.dto.portfolio;

import com.ssafy.queant.model.entity.portfolio.Portfolio;
import com.ssafy.queant.model.entity.product.CustomProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioResponseDto {
    List<Portfolio> portfolioList;
    List<CustomProduct> customProductList;
}
