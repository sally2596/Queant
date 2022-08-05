package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.portfolio.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {

<<<<<<< HEAD
    Optional<List<Portfolio>> findByMemberMemberIdAndPortfolioNo(UUID memberId, int portfolioNo);
=======
    Optional<List<Portfolio>> findByMemberAndPortfolioNo(UUID memberId, int portfolioNo);
>>>>>>> 5ce37d422681cd38f2f850226acdf9974fee4b21
}
