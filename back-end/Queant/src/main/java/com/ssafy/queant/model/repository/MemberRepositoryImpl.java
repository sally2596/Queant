package com.ssafy.queant.model.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.member.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl extends Member {

    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    public Member memberList(){
        //JPAQuery<?> query = new JPAQuery<Void>(entityManager);

        QMember member = QMember.member;
        Member m = queryFactory.selectFrom(member)
                .where(member.email.eq("string"))
                .fetchFirst();
        return m;
    }


}
