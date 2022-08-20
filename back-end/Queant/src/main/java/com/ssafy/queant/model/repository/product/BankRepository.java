package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Integer> {

    List<Bank> findAll();

    Optional<Bank> findByBankId(int bankId);

    @Query(value = "select bank_id from bank where bank_name=:bankName", nativeQuery = true)
    int searchBankID(@Param("bankName") String bankName);
}
