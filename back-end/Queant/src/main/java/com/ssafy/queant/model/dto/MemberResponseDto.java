package com.ssafy.queant.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class MemberResponseDto {
    List<MemberDto> memberDtoList;
    int totalPage;

}
