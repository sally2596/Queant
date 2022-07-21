package com.ssafy.queant.security;

import com.ssafy.queant.model.dto.MemberDto;
import com.ssafy.queant.model.entity.Member;
import com.ssafy.queant.model.entity.MemberRole;
import com.ssafy.queant.model.repository.MemberRepository;
import com.ssafy.queant.model.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. memberemail : {}", username);
        Optional<Member> result = memberRepository.findByEmail(username);
        if(!result.isPresent()) throw new UsernameNotFoundException("Check your Email again");
        Member member = result.get();

        log.info("[loadUserByUsername] Member Entity MemberDto 로 전환시작");

        MemberDto memberDto = modelMapper.map(member, MemberDto.class);
//        MemberDto memberDto= MemberDto.builder()
//                .email(member.getEmail())
//                .member_id(member.getMember_id())
//                .password(member.getPassword())
//                .name(member.getName())
//                .gender(member.getGender())
//                .birthdate(member.getBirthdate())
//                .portfolio_cnt(member.getPortfolio_cnt())
//                .social(member.getSocial())
//                .refreshToken(member.getRefreshToken())
//                .roleSet(member.getRoleSet()).build();

        return memberDto;
    }
}
