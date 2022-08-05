package com.ssafy.queant.model.dto.product;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BankDto {
    private int bankId;
    private String scodeId;
    private String bankName;
    private String shortName;
    private String homepage;
    private String tel;
    private String picture;
}
