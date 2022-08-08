package com.ssafy.queant.model.dto.Search;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SearchRequestDto {
    Long amount;
    Integer period;
    Boolean isSimpleInterest; // true: 단리, false: 복리
    Boolean isFixed; // true: 정액적립, false: 자유적립
    List<BankKeywordDto> bank;
    List<SpecificCodeDto> joinway; // 가입방법
    List<SpecificCodeDto> conditions; // 우대사항
    List<SpecificCodeDto> bankType; // 은행 타입
    List<SpecificCodeDto> traitSet; // 특징
}
