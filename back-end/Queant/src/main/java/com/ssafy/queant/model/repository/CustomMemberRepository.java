package com.ssafy.queant.model.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.member.MemberRole;
import com.ssafy.queant.model.entity.member.Social;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.ssafy.queant.model.entity.member.QMember.member;

public interface CustomMemberRepository {
    public Page<Member> memberList(Social social, MemberRole memberRole, Pageable pageable);

}
