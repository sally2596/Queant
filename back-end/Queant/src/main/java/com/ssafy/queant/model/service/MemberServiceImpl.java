package com.ssafy.queant.model.service;

import com.ssafy.queant.model.entity.Member;
import com.ssafy.queant.model.repository.MemberRepository;
import com.ssafy.queant.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String register(String email, String password, String name) {
        log.info("[register] 회원가입 정보 전달");
        Member member = Member.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .refreshToken(jwtTokenProvider.createRefreshToken())
                .build();

        Member savedMember = memberRepository.save(member);

        return null;
    }

    @Override
    public String login(String id, String password) throws RuntimeException {
        return null;
    }

    @Override
    public boolean emailCheck(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.isPresent();
    }
}
