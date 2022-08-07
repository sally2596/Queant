package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.portfolio.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    //Optional<List<Portfolio>> findByMemberMemberIdAndPortfolioNo(UUID memberId, int portfolioNo);

}
