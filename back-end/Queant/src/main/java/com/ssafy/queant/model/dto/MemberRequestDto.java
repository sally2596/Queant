package com.ssafy.queant.model.dto;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class MemberRequestDto {
    @Schema(description = "이메일 주소")
    String email;
    @Schema(description ="비밀번호")
    String password;
    @Schema(description = "이메일 인증 코드")
    String code;
    @Schema(description ="리프레시 토큰")
    String refreshtoken;
}
