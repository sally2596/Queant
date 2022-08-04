package com.ssafy.queant.model.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioRequestDto {
    List<PortfolioDto> portfolioDtoList;
    String email;
}
