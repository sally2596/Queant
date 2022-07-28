package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.product.BankDto;
import com.ssafy.queant.model.entity.product.Bank;
import com.ssafy.queant.model.repository.BankRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankServiceImpl implements BankService{

    private final BankRepository bankRepository;
    private final ModelMapper modelMapper;

    public BankServiceImpl(BankRepository bankRepository, ModelMapper modelMapper) {
        this.bankRepository = bankRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BankDto> findAll() {
        List<Bank> list = bankRepository.findAll();
        List<BankDto> result=new ArrayList<>();

        for (Bank b:list) {
            result.add(modelMapper.map(b,BankDto.class));
        }

        return result;
    }

    @Override
    public BankDto findByBankId(int bankId) {
        Optional<Bank> bank = bankRepository.findByBankId(bankId);
        if(bank.isPresent()){
            BankDto bankDto = modelMapper.map(bank.get(),BankDto.class);
            return bankDto;
        }
        return null;
    }


}
