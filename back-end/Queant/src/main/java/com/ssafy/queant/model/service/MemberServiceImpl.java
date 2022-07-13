package com.ssafy.queant.model.service;

import com.ssafy.queant.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. memberemail : {}", username);
        return memberRepository.findByEmail(username);
    }
}
