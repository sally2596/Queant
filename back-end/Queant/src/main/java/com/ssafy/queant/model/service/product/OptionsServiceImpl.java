package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.OptionsDto;
import com.ssafy.queant.model.entity.product.Options;
import com.ssafy.queant.model.repository.SpecificCodeRepository;
import com.ssafy.queant.model.repository.product.OptionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OptionsServiceImpl implements OptionsService{

    private final OptionsRepository optionsRepository;
    private final ModelMapper modelMapper;

    public OptionsServiceImpl(OptionsRepository optionsRepository, SpecificCodeRepository specificCodeRepository, ModelMapper modelMapper) {
        this.optionsRepository = optionsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OptionsDto> findByProductId(String productId) {
        List<Options> list = optionsRepository.findByProductId(productId);

        List<OptionsDto> result = new ArrayList<>();

        if(list.size()>0){
            for(Options o:list){
                result.add(modelMapper.map(o,OptionsDto.class));
            }
        }

        return result;
    }
}
