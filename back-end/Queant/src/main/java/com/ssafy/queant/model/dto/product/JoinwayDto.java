package com.ssafy.queant.model.dto.product;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JoinwayDto {
    private int joinwayId;
    private String value;
    private int productId;
    private String scodeId;
}
