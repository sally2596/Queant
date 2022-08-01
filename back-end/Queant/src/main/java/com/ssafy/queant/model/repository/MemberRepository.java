package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.member.MemberRole;
import com.ssafy.queant.model.entity.member.Social;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = {"roleSet"}, type=EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.email =:email")
    Optional<Member> findByEmail(@Param("email") String email);

    //@EntityGraph(attributePaths = {"roleSet"}, type=EntityGraph.EntityGraphType.LOAD)
    @Query("From Member m where :role member m.roleSet ORDER BY m.roleSet.size")
    Page<Member> findByRole(@Param("role") MemberRole role, Pageable pageable);

    @EntityGraph(attributePaths = {"roleSet"}, type=EntityGraph.EntityGraphType.LOAD)
    @Query("From Member m where m.social = :social")
    Page<Member> findBySocial(@Param("social") Social social, Pageable pageable);

}
