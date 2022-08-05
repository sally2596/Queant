package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.Search.BankKeywordDto;
import com.ssafy.queant.model.dto.Search.SearchKeywordDto;
import com.ssafy.queant.model.dto.Search.SpecificCodeDto;
import com.ssafy.queant.model.entity.SpecificCode;
import com.ssafy.queant.model.entity.product.Bank;
import com.ssafy.queant.model.repository.SpecificCodeRepository;
import com.ssafy.queant.model.repository.product.BankRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    private final SpecificCodeRepository specificCodeRepository;
    private final BankRepository bankRepository;
    private final ModelMapper modelMapper;

    public SearchServiceImpl(SpecificCodeRepository specificCodeRepository, BankRepository bankRepository, ModelMapper modelMapper) {
        this.specificCodeRepository = specificCodeRepository;
        this.bankRepository = bankRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SearchKeywordDto getSearchKeyword() {
        List<SpecificCode> specificJoin = specificCodeRepository.findByCodeId("A");
        List<SpecificCode> specificCondition = specificCodeRepository.findByCodeId("B");
        List<SpecificCode> specificBankType = specificCodeRepository.findByCodeId("C");
        List<SpecificCode> specificTraitSet = specificCodeRepository.findByCodeId("E");

        List<SpecificCodeDto> joinway = new ArrayList<>();
        List<SpecificCodeDto> conditions = new ArrayList<>();
        List<SpecificCodeDto> bankType = new ArrayList<>();
        List<SpecificCodeDto> traitSet = new ArrayList<>();

        for (SpecificCode j : specificJoin) {
            joinway.add(modelMapper.map(j, SpecificCodeDto.class));
        }

        for (SpecificCode c : specificCondition) {
            conditions.add(modelMapper.map(c, SpecificCodeDto.class));
        }

        for (SpecificCode b : specificBankType) {
            bankType.add(modelMapper.map(b, SpecificCodeDto.class));
        }

        for (SpecificCode t : specificTraitSet) {
            traitSet.add(modelMapper.map(t, SpecificCodeDto.class));
        }

        List<Bank> bank = bankRepository.findAll();
        List<BankKeywordDto> bankDto = new ArrayList<>();
        for (Bank b : bank) {
            bankDto.add(modelMapper.map(b, BankKeywordDto.class));
        }

        SearchKeywordDto searchKeywordDto = SearchKeywordDto.builder()
                .joinway(joinway)
                .bankType(bankType)
                .conditions(conditions)
                .bank(bankDto)
                .traitSet(traitSet)
                .build();

        return searchKeywordDto;
    }
}
