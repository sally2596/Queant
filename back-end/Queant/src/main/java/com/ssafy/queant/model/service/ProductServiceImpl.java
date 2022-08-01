package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.product.ProductDto;
import com.ssafy.queant.model.entity.product.Product;
import com.ssafy.queant.model.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDto> findByBankId(int bankId){
        List<Product> list = productRepository.findByBankId(bankId);
        List<ProductDto> result = new ArrayList<>();

        for(Product p:list){
            result.add(modelMapper.map(p,ProductDto.class));
        }
        return result;
    }
}
