package com.ssafy.queant.model.dto.product;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OptionsDto {
    private int optionId;
    private String productId;
    private float baseRate;
    private Float highBaseRate;
    private int saveTerm;
    private boolean rateType; // 단리 복리
    private Boolean rsrvType; // 자유 적립식, 정액 적립식
}
