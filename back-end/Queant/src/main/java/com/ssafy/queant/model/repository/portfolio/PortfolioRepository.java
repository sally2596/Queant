package com.ssafy.queant.model.repository.portfolio;

import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.portfolio.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    Optional<List<Portfolio>> findPortfolioByMemberAndPortfolioNo(Member member, int portfolioNo);
    Optional<List<Portfolio>> findPortfolioByMember(Member member);

}
