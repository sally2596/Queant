package com.ssafy.queant.controller.OAuth;

import com.ssafy.queant.model.dto.member.LoginResultDto;
import com.ssafy.queant.model.dto.member.MemberDto;
import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.member.MemberRole;
import com.ssafy.queant.model.repository.MemberRepository;
import com.ssafy.queant.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
        log.info("[member] : "+member);
        member.setRoleSet(new HashSet<>());
        member.addMemberRole(MemberRole.ROLE_USER);
        //member.setSocial(memberDto.getSocial());
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

        if(member.getSocial()!=memberDto.getSocial()){
            log.info("[login] 이미 가입한 계정, 다른 로그인 경로 사용할 것");
            loginResultDto.setResult(member.getSocial().toString());
            return loginResultDto;
        }

        log.info("[login] 로그인 성공!");
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

    public ResponseEntity<Map<String, String>> makeResponseEntity(MemberDto member, LoginResultDto result) {
        Map<String, String> response = new HashMap<>();
        if(result.getResult().equals("SUCCESS")){
            response.put("email",member.getEmail());
            response.put("AccessToken", jwtTokenProvider.createToken(member.getEmail()));
            response.put("RefreshToken", result.getRefreshToken());
            log.info("[소셜 로그인 성공]: "+response);
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
        } else{
            response.put("Login", result.getResult());
            log.info("[소셜 로그인 실패]: "+response);
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.CONFLICT);
        }
    }
}
