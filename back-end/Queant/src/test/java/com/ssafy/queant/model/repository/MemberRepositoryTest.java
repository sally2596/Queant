package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.dto.member.MemberDto;
import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.member.MemberRole;
import com.ssafy.queant.model.entity.member.Social;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private CustomMemberRepository memberRepository;

    @Test
    void memberList() {
        Pageable pageable = PageRequest.of(0,2);
        Page<Member> members = memberRepository.memberList(Social.None, MemberRole.ROLE_USER, pageable);

        members.get().forEach(member -> System.out.println(member.toString()));
    }
}