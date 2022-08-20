package com.ssafy.queant.model.dto.portfolio;


import com.ssafy.queant.model.dto.product.CustomProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioRequestDto {
    List<PortfolioDto> portfolioDtoList;
    PortfolioDto portfolioDto;
    CustomProductDto customProductDto;
    UUID memberId;
    int portfolioId;
    int portfolioNo;
    int portfolioCnt;
}
