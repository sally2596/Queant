package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.product.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Integer> {

    List<Bank> findAll();

    Optional<Bank> findByBankId(int bankId);

}
