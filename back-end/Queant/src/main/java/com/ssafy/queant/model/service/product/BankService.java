package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.BankDto;

import java.util.List;

public interface BankService {
    List<BankDto> findAll();

    BankDto findByBankId(int bankId);
}
