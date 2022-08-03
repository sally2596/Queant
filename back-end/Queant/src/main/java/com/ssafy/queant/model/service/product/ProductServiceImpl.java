package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.ProductDto;
import com.ssafy.queant.model.dto.product.ProductInfoDto;
import com.ssafy.queant.model.entity.product.Bank;
import com.ssafy.queant.model.entity.product.Product;
import com.ssafy.queant.model.repository.product.BankRepository;
import com.ssafy.queant.model.repository.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final BankRepository bankRepository;

    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, BankRepository bankRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.bankRepository = bankRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDto> findByBankId(int bankId){
        List<Product> list = productRepository.findByBankId(bankId);

        Optional<Bank> bankResult= bankRepository.findByBankId(bankId);
        String bankName = bankResult.get().getBankName();

        List<ProductDto> result = new ArrayList<>();

        for(Product p:list){
            ProductDto dto = modelMapper.map(p,ProductDto.class);
            dto.setBankName(bankName);
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<ProductDto> findByNameContaining(String name) {
        List<Product> list = productRepository.findByNameContaining(name);
        List<ProductDto> result = new ArrayList<>();

        for(Product p:list){
            ProductDto dto = modelMapper.map(p,ProductDto.class);

            Optional<Bank> bankResult= bankRepository.findByBankId(dto.getBankId());
            dto.setBankName(bankResult.get().getBankName());
            result.add(dto);
        }
        return result;
    }

    @Override
    public Optional<ProductInfoDto> findByProductId(String productId) {
        return Optional.empty();
    }
}
