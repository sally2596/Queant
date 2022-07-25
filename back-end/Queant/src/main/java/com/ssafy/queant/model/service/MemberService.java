package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.LoginResultDto;
import com.ssafy.queant.model.dto.MemberDto;

import java.util.UUID;

public interface MemberService {
    boolean register(MemberDto memberDto);
    LoginResultDto login(String email, String password) throws RuntimeException;
    boolean emailCheck(String email);

    //회원정보 조회
    MemberDto findMember(String email);
    //회원정보 변경
    MemberDto updateMember(MemberDto memberDto);
    //회원 탈퇴
    boolean disableMember(String email);

}
