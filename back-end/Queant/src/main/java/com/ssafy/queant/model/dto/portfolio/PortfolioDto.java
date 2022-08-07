package com.ssafy.queant.model.dto.portfolio;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.queant.model.dto.product.ProductDto;
import lombok.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PortfolioDto {
    @Builder.Default
    private int portfolioId=-1;
    private int portfolioNo;
    private int productId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ProductDto product;
    private Long amount;
    private Long amountFixed;
    private Date startDate;
    private Date endDate;
    private int optionId;
    private List<Integer> conditionIds = new ArrayList<>();


}
