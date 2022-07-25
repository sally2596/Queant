package com.ssafy.queant.model.dto.member;

import com.ssafy.queant.model.dto.member.MemberDto;
import lombok.Data;

import java.util.List;

@Data
public class MemberResponseDto {
    List<MemberDto> memberDtoList;
    int totalPage;

}
