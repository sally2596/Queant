package com.ssafy.queant.model.dto.product;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BankResponseDto {
    private BankDto bank;
    private List<ProductDto> productList;
}
