package com.ssafy.queant.model.dto.product;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SearchResponseDto {
    List<ProductDto> productDtoList;
    int totalPage;
    long totalCount;
}
