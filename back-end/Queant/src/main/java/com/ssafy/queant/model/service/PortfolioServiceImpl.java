package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.product.CustomProductDto;
import com.ssafy.queant.model.entity.product.CustomProduct;
import com.ssafy.queant.model.repository.CustomProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PortfolioServiceImpl implements PortfolioService{

   private final CustomProductRepository customProductRepository;
   private final ModelMapper modelMapper;

   @Autowired
   public PortfolioServiceImpl(CustomProductRepository customProductRepository, ModelMapper modelMapper) {
      this.customProductRepository = customProductRepository;
      this.modelMapper = modelMapper;
   }

   @Override
   public boolean registCustomProduct(CustomProductDto customProductDto) throws RuntimeException{
      log.info("[사용자정의 상품 추가]");
      CustomProduct customProduct = modelMapper.map(customProductDto,CustomProduct.class);

      return false;
   }

   @Override
   public List<CustomProductDto> findCustomProductByMemberEmail(String email) throws RuntimeException {
      return null;
   }
}
