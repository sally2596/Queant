package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.JoinwayDto;
import com.ssafy.queant.model.entity.SpecificCode;
import com.ssafy.queant.model.entity.product.Joinway;
import com.ssafy.queant.model.repository.SpecificCodeRepository;
import com.ssafy.queant.model.repository.product.JoinwayRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JoinwayServiceImpl implements JoinwayService {
    private final JoinwayRepository joinwayRepository;
    private final SpecificCodeRepository specificCodeRepository;
    private final ModelMapper modelMapper;

    public JoinwayServiceImpl(JoinwayRepository joinwayRepository, SpecificCodeRepository specificCodeRepository, ModelMapper modelMapper) {
        this.joinwayRepository = joinwayRepository;
        this.specificCodeRepository = specificCodeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<JoinwayDto> findByProductId(String productId){
        List<Joinway> list = joinwayRepository.findByProductId(productId);

        List<SpecificCode> specificCodeList = specificCodeRepository.findByCodeId("A");
        Map<String,String> valueMap = specificCodeList.stream().collect(Collectors.toMap(SpecificCode::getScodeId,
                SpecificCode::getScodeValue));

        List<JoinwayDto> result = new ArrayList<>();

        if(list.size()>0){
            for(Joinway j:list){
                JoinwayDto joinwayDto = modelMapper.map(j,JoinwayDto.class);
                joinwayDto.setValue(valueMap.get(joinwayDto.getScodeId()));
                result.add(joinwayDto);
            }
        }

        return result;
    }
}
