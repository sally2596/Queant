package com.ssafy.queant.model.service.OAuth;

import com.ssafy.queant.model.dto.LoginResultDto;
import com.ssafy.queant.model.dto.MemberDto;
import com.ssafy.queant.model.entity.Member;
import com.ssafy.queant.model.entity.MemberRole;
import com.ssafy.queant.model.repository.MemberRepository;
import com.ssafy.queant.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@Slf4j
public class OAuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public OAuthService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, ModelMapper modelMapper){
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public boolean register(MemberDto memberDto) {
        log.info("[register] 회원가입 정보 전달");
        Member member = modelMapper.map(memberDto, Member.class);
        member.setRoleSet(new HashSet<>());
        member.addMemberRole(MemberRole.ROLE_USER);
        member.setSocial(member.getSocial());
        Member savedMember = memberRepository.save(member);

        if(!savedMember.getName().isEmpty()){
            log.info("[register] : 회원가입 완료");
            return true;
        } else {
            log.info("[register] : 회원가입 실패");
            return false;
        }
    }

    public LoginResultDto login(MemberDto memberDto) throws RuntimeException {
        Optional<Member> result = memberRepository.findByEmail(memberDto.getEmail());
        LoginResultDto loginResultDto = new LoginResultDto();
        Member member = result.get();

        log.info("[login] 로그인 성공!");

        if(member.getSocial()!=memberDto.getSocial()){
            log.info("[login] :"+memberDto.getSocial()+"로 가입함");
            loginResultDto.setResult("PlatformError");
            return loginResultDto;
        }

        //RefreshToken 주입
        loginResultDto.setResult("SUCCESS");
        String refreshToken = jwtTokenProvider.createRefreshToken();
        loginResultDto.setRefreshToken(refreshToken);
        member.setRefreshToken(refreshToken);
        memberRepository.save(member);

        return loginResultDto;
    }

    public boolean emailCheck(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.isPresent();
    }
}
