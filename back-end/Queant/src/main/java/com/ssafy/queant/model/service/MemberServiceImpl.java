package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.LoginResultDto;
import com.ssafy.queant.model.dto.MemberDto;
import com.ssafy.queant.model.entity.Member;
import com.ssafy.queant.model.entity.MemberRole;
import com.ssafy.queant.model.entity.Social;
import com.ssafy.queant.model.repository.MemberRepository;
import com.ssafy.queant.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, ModelMapper modelMapper){
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean register(MemberDto memberDto) throws RuntimeException{
        log.info("[register] 회원가입 정보 전달");
        log.info("[register] 비밀번호 : {}", memberDto.getPassword());
        Member member = modelMapper.map(memberDto, Member.class);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRoleSet(new HashSet<>());
        member.addMemberRole(MemberRole.ROLE_USER);
        member.setSocial(Social.None);
        Member savedMember = memberRepository.save(member);

        if(!savedMember.getName().isEmpty()){
            log.info("[register] : 회원가입 완료");
            return true;
        } else {
            log.info("[register] : 회원가입 실패");
            return false;
        }
    }

    @Override
    public LoginResultDto login(String email, String password) throws RuntimeException {
        Optional<Member> result = memberRepository.findByEmail(email);
        LoginResultDto loginResultDto = new LoginResultDto();
        if(!result.isPresent()) {
            log.info("[login] : 존재하지 않는 이메일입니다.");
            loginResultDto.setResult("EmailError");
            return loginResultDto;
        }
        Member member = result.get();
        if(!passwordEncoder.matches(password, member.getPassword())) {
            log.info("[login] : 패스워드가 일치하지 않습니다.");
            loginResultDto.setResult("PasswordError");
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

    @Override
    public boolean emailCheck(String email) throws RuntimeException{
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.isPresent();
    }

    @Override
    public MemberDto findMember(String email) throws RuntimeException{
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()){
            MemberDto memberDto = modelMapper.map(member.get(), MemberDto.class);
            return memberDto;
        }
        return null;
    }

    @Override
    public MemberDto updateMember(MemberDto memberDto) throws RuntimeException{
        Member member = modelMapper.map(memberDto,Member.class);
        memberRepository.save(member);
        Optional<Member> result = memberRepository.findByEmail(member.getEmail());
        if(result.isPresent()){
            memberDto = modelMapper.map(result.get(), MemberDto.class);
            return memberDto;
        }
        return null;
    }

    @Override
    public boolean disableMember(String email) throws RuntimeException{
        Optional<Member> result = memberRepository.findByEmail(email);
        if(!result.isPresent()) return false;
        Member member = result.get();
        member.setEnabled(false);
        memberRepository.save(member);
        return true;
    }

    @Override
    public boolean changePassword(String email, String password) throws RuntimeException {
        Optional<Member> result = memberRepository.findByEmail(email);
        if(!result.isPresent()) return false;
        Member member = result.get();
        member.setPassword(password);
        memberRepository.save(member);
        return true;
    }

}
