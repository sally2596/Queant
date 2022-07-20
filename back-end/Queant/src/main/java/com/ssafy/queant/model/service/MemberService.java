package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.LoginResultDto;
import com.ssafy.queant.model.dto.MemberDto;

public interface MemberService {
    boolean register(MemberDto memberDto);
    LoginResultDto login(String email, String password) throws RuntimeException;
    boolean emailCheck(String email);

}
