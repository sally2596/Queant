package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.ProductDto;
import com.ssafy.queant.model.dto.product.ProductInfoDto;

import java.util.List;
import java.util.Optional;

public interface ProductService{
    List<ProductDto> findByBankId(int bankId);
    List<ProductDto> findByNameContaining(String name);
    Optional<ProductInfoDto> findByProductId(String productId);
}
