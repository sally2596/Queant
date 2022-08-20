package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.ConditionsDto;
import com.ssafy.queant.model.entity.SpecificCode;
import com.ssafy.queant.model.entity.product.Conditions;
import com.ssafy.queant.model.repository.SpecificCodeRepository;
import com.ssafy.queant.model.repository.product.ConditionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConditionsServiceImpl implements ConditionsService {

    private final ConditionsRepository conditionsRepository;
    private final SpecificCodeRepository specificCodeRepository;
    private final ModelMapper modelMapper;

    public ConditionsServiceImpl(ConditionsRepository conditionsRepository, SpecificCodeRepository specificCodeRepository, ModelMapper modelMapper) {
        this.conditionsRepository = conditionsRepository;
        this.specificCodeRepository = specificCodeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ConditionsDto> findByProductId(int productId) {
        List<Conditions> list = conditionsRepository.findByProductId(productId);

        List<SpecificCode> specificCodeList = specificCodeRepository.findByCodeId("B");
        Map<String, String> valueMap = specificCodeList.stream().collect(Collectors.toMap(SpecificCode::getScodeId,
                SpecificCode::getScodeValue));

        List<ConditionsDto> result = new ArrayList<>();

        if (list.size() > 0) {
            for (Conditions c : list) {
                ConditionsDto conditionsDto = modelMapper.map(c, ConditionsDto.class);
                conditionsDto.setValue(valueMap.get(conditionsDto.getScodeId()));
                result.add(conditionsDto);
            }
        }

        return result;
    }
}
