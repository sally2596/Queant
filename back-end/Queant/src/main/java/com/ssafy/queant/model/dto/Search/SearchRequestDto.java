package com.ssafy.queant.model.dto.Search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Schema
public class SearchRequestDto {
    @Schema(description = "예치 금액")
    Long amount;

    @Schema(description = "예치 기간 (6,12,18,24,36)")
    Integer period;

    @Schema(description = "단리/복리 (true: 단리, false: 복리)")
    Boolean isSimpleInterest; // true: 단리, false: 복리

    @Schema(description = "적금 방식 (true: 정액적립, false: 자유적립)")
    Boolean isFixed; // true: 정액적립, false: 자유적립

    @Schema(description = "검색하고 싶은 은행 목록")
    List<BankKeywordDto> bank;

    @Schema(description = "검색하고 싶은 가입방법 목록")
    List<SpecificCodeDto> joinway; // 가입방법

    @Schema(description = "검색하고 싶은 우대사항 목록")
    List<SpecificCodeDto> conditions; // 우대사항

    @Schema(description = "가입하고 싶은 은행타입 목록 (일반은행, 저축은행, ")
    List<SpecificCodeDto> bankType; // 은행 타입

    @Schema(description = "가입하고 싶은 특징 목록")
    List<SpecificCodeDto> traitSet; // 특징
}
