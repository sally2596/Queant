package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.SpecificCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpecificCodeRepository  extends JpaRepository<SpecificCode, String> {
    Optional<SpecificCode> findByScodeId(String scodeId);
    List<SpecificCode> findByCodeId(String codeId);
}
