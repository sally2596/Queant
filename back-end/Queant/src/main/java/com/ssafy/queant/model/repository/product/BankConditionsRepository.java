package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankConditionsRepository extends JpaRepository<Bank, Integer> {
    
}
