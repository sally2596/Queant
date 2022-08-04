package com.ssafy.queant.model.dto.product;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TraitSetDto {
    private int traitSetId;
    private String value;
    private String productId;
    private String scodeId;
}
