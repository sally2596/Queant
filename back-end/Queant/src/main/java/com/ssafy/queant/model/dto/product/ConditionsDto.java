package com.ssafy.queant.model.dto.product;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ConditionsDto {
    private int conditionId;
    private String value;
    private int productId;
    private String scodeId;
    private float specialRate;
    private String conditionInfo;
}
