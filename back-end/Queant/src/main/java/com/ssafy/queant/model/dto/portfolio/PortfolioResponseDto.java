package com.ssafy.queant.model.dto.portfolio;

import com.ssafy.queant.model.dto.product.CustomProductDto;
import com.ssafy.queant.model.entity.portfolio.Portfolio;
import com.ssafy.queant.model.entity.product.CustomProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioResponseDto {
    List<PortfolioDto> portfolioList;
    List<CustomProductDto> customProductList;
    List<List<PortfolioDto>> portfolioListList;
}
