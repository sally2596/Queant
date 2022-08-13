package com.ssafy.queant.model.dto.member;

import com.ssafy.queant.model.entity.member.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Schema
@Data
public class MemberRequestDto {
    @Schema(description = "이메일 주소")
    String email;
    @Schema(description ="비밀번호")
    String password;
    @Schema(description ="새 비밀번호")
    String newPassword;
    @Schema(description = "이메일 인증 코드")
    String code;
    @Schema(description ="리프레시 토큰")
    String refreshtoken;
    @Schema(description = "회원 권한 목록")
    Set<MemberRole> roles;
}
