package com.ssafy.queant.model.dto.Search;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SearchKeywordDto {
    List<BankKeywordDto> bank;
    List<SpecificCodeDto> joinway;
    List<SpecificCodeDto> conditions; // 우대사항
    List<SpecificCodeDto> bankType; // 은행 타입
}
