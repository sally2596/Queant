package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.TraitSetDto;
import com.ssafy.queant.model.entity.SpecificCode;
import com.ssafy.queant.model.entity.product.TraitSet;
import com.ssafy.queant.model.repository.SpecificCodeRepository;
import com.ssafy.queant.model.repository.product.TraitSetRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TraitSetServiceImpl implements TraitSetService {
    private final TraitSetRepository traitSetRepository;
    private final SpecificCodeRepository specificCodeRepository;
    private final ModelMapper modelMapper;

    public TraitSetServiceImpl(TraitSetRepository traitSetRepository, SpecificCodeRepository specificCodeRepository, ModelMapper modelMapper) {
        this.traitSetRepository = traitSetRepository;
        this.specificCodeRepository = specificCodeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TraitSetDto> findByProductId(int productId) {
        List<TraitSet> list = traitSetRepository.findByProductId(productId);

        List<SpecificCode> specificCodeList = specificCodeRepository.findByCodeId("E");
        Map<String, String> valueMap = specificCodeList.stream().collect(Collectors.toMap(SpecificCode::getScodeId,
                SpecificCode::getScodeValue));

        List<TraitSetDto> result = new ArrayList<>();

        if (list.size() > 0) {
            for (TraitSet t : list) {
                TraitSetDto traitSetDto = modelMapper.map(t, TraitSetDto.class);
                traitSetDto.setValue(valueMap.get(traitSetDto.getScodeId()));
                result.add(traitSetDto);
            }
        }

        return result;
    }
}
