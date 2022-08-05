package com.ssafy.queant.model.dto.Search;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BankKeywordDto {
    String scodeId;
    String bankId;
    String bankName;
    String shortName;
}
