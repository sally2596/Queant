package com.ssafy.queant.model.service;

import com.querydsl.core.Tuple;
import com.ssafy.queant.model.dto.Search.BankKeywordDto;
import com.ssafy.queant.model.dto.Search.SearchKeywordDto;
import com.ssafy.queant.model.dto.Search.SearchRequestDto;
import com.ssafy.queant.model.dto.Search.SpecificCodeDto;
import com.ssafy.queant.model.dto.product.ProductDto;
import com.ssafy.queant.model.dto.product.SearchResponseDto;
import com.ssafy.queant.model.entity.SpecificCode;
import com.ssafy.queant.model.entity.product.Bank;
import com.ssafy.queant.model.entity.product.Product;
import com.ssafy.queant.model.repository.SpecificCodeRepository;
import com.ssafy.queant.model.repository.product.BankRepository;
import com.ssafy.queant.model.repository.product.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SpecificCodeRepository specificCodeRepository;
    private final BankRepository bankRepository;
    private final SearchRepository searchRepository;
    private final ModelMapper modelMapper;


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

    @Override
    public SearchResponseDto searchSingle(SearchRequestDto searchRequestDto, boolean isDeposit, int page) {
        Pageable pageable = PageRequest.of(page - 1, 50);

        List<BankKeywordDto> bankKeywordDtos = searchRequestDto.getBank();
        List<Integer> bank = new ArrayList<>();
        if (bankKeywordDtos.size() > 0) {
            for (BankKeywordDto b : bankKeywordDtos)
                bank.add(b.getBankId());
        }

        List<SpecificCodeDto> joinwayDto = searchRequestDto.getJoinway();
        List<String> joinway = new ArrayList<>();
        if (joinwayDto.size() > 0) {
            for (SpecificCodeDto s : joinwayDto) {
                joinway.add(s.getScodeId());
            }
        }

        List<SpecificCodeDto> conditionsDto = searchRequestDto.getConditions();
        List<String> conditions = new ArrayList<>();
        if (conditionsDto.size() > 0) {
            for (SpecificCodeDto s : conditionsDto) {
                conditions.add(s.getScodeId());
            }
        }
        List<SpecificCodeDto> traitSetDto = searchRequestDto.getTraitSet();
        List<String> traitSet = new ArrayList<>();
        if (traitSetDto.size() > 0) {
            for (SpecificCodeDto s : traitSetDto) {
                traitSet.add(s.getScodeId());
            }
        }

        Page<Tuple> result = searchRepository.searchSingle(
                searchRequestDto.getAmount(),
                isDeposit,
                searchRequestDto.getIsSimpleInterest(),
                searchRequestDto.getIsFixed(),
                searchRequestDto.getPeriod(),
                bank,
                joinway,
                conditions,
                traitSet,
                pageable);

        List<ProductDto> productDtoList = new ArrayList<>();

        result.get().forEach(product -> {
            ProductDto productDto = modelMapper.map(product.get(0, Product.class), ProductDto.class);
            productDto.setBaseRate(product.get(1, Float.class));
            productDtoList.add(productDto);
        });

        SearchResponseDto searchResponseDto = SearchResponseDto.builder()
                .productDtoList(productDtoList)
                .totalCount(result.getTotalElements())
                .totalPage(result.getTotalPages())
                .build();
        return searchResponseDto;
    }
}
