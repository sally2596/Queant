package com.ssafy.queant.model.dto.product;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDto {
    private int productId;
    private String productCode;
    private int bankId;
    private String bankName;
    private String name;
    private String scodeId;
    private boolean isDeposit;
    private int ageMin;
    private int ageMax;
    private Long budgetMin;
    private Long budgetMax;
    private int termMin;
    private int termMax;
    private String etc;
    private boolean isEnabled;
    private float baseRate;
    private float specialRateSum;
    private float totalRate;
    private List<ConditionsDto> appliedSpecialRate;
    private String picture;
    private int selectedOptionId;
}
