package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.member.LoginResultDto;
import com.ssafy.queant.model.dto.member.MemberDto;
import com.ssafy.queant.model.dto.member.MemberResponseDto;
import com.ssafy.queant.model.entity.member.MemberRole;
import com.ssafy.queant.model.entity.member.Social;

import java.util.Set;

public interface MemberService {
    boolean register(MemberDto memberDto) throws RuntimeException;
    LoginResultDto login(String email, String password) throws RuntimeException;
    boolean emailCheck(String email)throws RuntimeException;

    //회원정보 조회
    MemberDto findMember(String email)throws RuntimeException;
    //회원정보 변경
    MemberDto updateMember(MemberDto memberDto)throws RuntimeException;
    //회원 탈퇴
    boolean disableMember(String email)throws RuntimeException;
    //비밀번호 변경
    boolean changePassword(String email, String password) throws RuntimeException;
    //권한 업데이트
    MemberDto updateRoles(String email, Set<MemberRole> roleSet) throws RuntimeException;

    MemberResponseDto memberListByRoles(MemberRole role, int page) throws RuntimeException;

    MemberResponseDto memberListBySocial(Social social, int page) throws RuntimeException;

    MemberResponseDto memberList(Social social, MemberRole memberRole, int page) throws RuntimeException;

}
