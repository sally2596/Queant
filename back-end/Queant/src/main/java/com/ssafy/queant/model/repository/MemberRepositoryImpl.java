package com.ssafy.queant.model.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.member.MemberRole;
import com.ssafy.queant.model.entity.member.Social;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.ssafy.queant.model.entity.member.QMember.member;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements CustomMemberRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Member> memberList(Social social, MemberRole memberRole, Pageable pageable) {

        QueryResults<Member> results =  queryFactory
                .selectFrom(member)
                .where(eqSocial(social), eqMemberRole(memberRole))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(member.roleSet.size().desc(), member.email.asc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }


    private BooleanExpression eqSocial(Social social){
        if(social == null) return null;
        return member.social.eq(social);
    }

    private BooleanExpression eqMemberRole(MemberRole memberRole){
        if(memberRole == null) return null;
        return member.roleSet.contains(memberRole);
    }


}
