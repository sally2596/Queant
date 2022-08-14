package com.ssafy.queant.model.dto.product;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UpdateDetailDto {
    private UpdateProductDto product;
    private List<OptionsDto> options;
    private List<ConditionsDto> conditions;
    private List<JoinwayDto> joinway;
    private List<TraitSetDto> traitSet;
}
