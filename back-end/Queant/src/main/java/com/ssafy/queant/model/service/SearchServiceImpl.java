package com.ssafy.queant.model.service;

import com.querydsl.core.Tuple;
import com.ssafy.queant.model.dto.Search.BankKeywordDto;
import com.ssafy.queant.model.dto.Search.SearchKeywordDto;
import com.ssafy.queant.model.dto.Search.SearchRequestDto;
import com.ssafy.queant.model.dto.Search.SpecificCodeDto;
import com.ssafy.queant.model.dto.product.ProductDto;
import com.ssafy.queant.model.entity.SpecificCode;
import com.ssafy.queant.model.entity.product.Bank;
import com.ssafy.queant.model.entity.product.Product;
import com.ssafy.queant.model.repository.SpecificCodeRepository;
import com.ssafy.queant.model.repository.product.BankRepository;
import com.ssafy.queant.model.repository.product.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public List<ProductDto> searchSingle(SearchRequestDto searchRequestDto, boolean isDeposit) {

        List<Tuple> result = searchRepository.searchSingle(
                searchRequestDto.getAmount(),
                isDeposit,
                searchRequestDto.getIsSimpleInterest(),
                searchRequestDto.getIsFixed(),
                searchRequestDto.getPeriod(),
                searchRequestDto.getBank(),
                searchRequestDto.getJoinway(),
                searchRequestDto.getConditions(),
                searchRequestDto.getTraitSet());

        HashMap<Integer, ProductDto> map = new HashMap<>();
        result.forEach(r -> {
            ProductDto productDto = modelMapper.map(r.get(0, Product.class), ProductDto.class);
            Float baseRate = r.get(1, Float.class);
            Float specialRate = null;
            String scodeId = null;
            if (searchRequestDto.getConditions().size() > 0) {
                specialRate = r.get(3, Float.class);
                scodeId = r.get(4, String.class);
            }
            Integer optionId = r.get(2, Integer.class);
            ProductDto p;
            HashMap<String, Float> appliedSpecialRate = null;

            if (map.containsKey(productDto.getProductId())) { //이미 존재하는 경우 우대 금리가 더 있다는 뜻
                p = map.get(productDto.getProductId());
                appliedSpecialRate = p.getAppliedSpecialRate();
            } else {
                p = productDto;
                p.setTotalRate(p.getTotalRate() + baseRate);
            }

            if (appliedSpecialRate == null) // 우대금리 map이 없으면 만들기
                appliedSpecialRate = new HashMap<>();
            if (scodeId != null && specialRate != null) { // 우대 금리가 있으면
                p.setSpecialRateSum(p.getSpecialRateSum() + specialRate); // 우대 금리 합산
                appliedSpecialRate.put(scodeId, specialRate); //우대 금리 조건, 우대금리 값 넣기
            }

            p.setAppliedSpecialRate(appliedSpecialRate);
            p.setBaseRate(baseRate);
            p.setSelectedOptionId(optionId);
            p.setTotalRate(p.getTotalRate() + (specialRate != null ? specialRate : 0));

            map.put(p.getProductId(), p);
        });

        List<ProductDto> list = new ArrayList<>();
        for (Integer key : map.keySet()) {
            list.add(map.get(key));
        }
        Collections.sort(list, new Comparator<ProductDto>() {
            @Override
            public int compare(ProductDto o1, ProductDto o2) {
                if (o2.getTotalRate() > o1.getTotalRate())
                    return 1;
                else if (o2.getTotalRate() < o1.getTotalRate())
                    return -1;
                else return 0;
            }
        });

        return list;
    }
}
