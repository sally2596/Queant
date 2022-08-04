package com.ssafy.queant.model.dto.portfolio;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.queant.model.dto.product.ProductDto;
import lombok.*;


import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PortfolioDto {
    private String productId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ProductDto product;
    private int portfolioNo;
    private Long amount;
    private Date start_date;
    private Date end_date;
    private float special_rate;
    private Long amount_fixed;


}
