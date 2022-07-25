package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.Gender;
import com.ssafy.queant.model.entity.Member;
import com.ssafy.queant.model.entity.MemberRole;
import com.ssafy.queant.model.entity.Social;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = {"roleSet"}, type=EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.email =:email")
    Optional<Member> findByEmail(@Param("email") String email);
//@Query("select m from Member m where m.roleSet ")
//    List<Object[]> getMemberwithRole(@Param("role") MemberRole role);
//    List<Object[]> getMemberwithSocial(@Param("social") Social social);

}
