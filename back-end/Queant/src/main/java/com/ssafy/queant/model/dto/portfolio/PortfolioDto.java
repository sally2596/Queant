package com.ssafy.queant.model.dto.portfolio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.queant.model.dto.product.ConditionsDto;
import com.ssafy.queant.model.dto.product.OptionsDto;
import com.ssafy.queant.model.dto.product.ProductDto;
import lombok.*;


import java.util.*;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date endDate;
    private int optionId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OptionsDto option;
    @Builder.Default
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<ConditionsDto> conditions = new HashSet<>();
    @Builder.Default
    private List<Integer> conditionIds = new ArrayList<>();


    public void addConditionIds(){
        for(ConditionsDto condition : conditions){
            conditionIds.add(condition.getConditionId());
        }
    }

}
