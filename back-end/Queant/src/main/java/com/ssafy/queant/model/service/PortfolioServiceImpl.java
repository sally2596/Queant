package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.product.CustomProductDto;
import com.ssafy.queant.model.entity.product.CustomProduct;
import com.ssafy.queant.model.repository.product.CustomProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.*;

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
   public CustomProductDto registCustomProduct(CustomProductDto customProductDto, UUID memberId) throws RuntimeException{

      // dto -> entity
      // 저장
      // 저장한 값 entity -> dto 변환후 반환
      log.info("[사용자 정의 상품 추가]");

      CustomProduct customProduct = modelMapper.map(customProductDto,CustomProduct.class);
      customProduct.setMemberId(memberId);

      CustomProduct savedCustomProduct = customProductRepository.save(customProduct);

      CustomProductDto savedCustomProductDto = modelMapper.map(savedCustomProduct,CustomProductDto.class);

      return savedCustomProductDto;
   }

   @Override
   public boolean deleteCustomProduct(CustomProductDto customProductDto) throws RuntimeException{
      log.info("[사용자 정의 상품 삭제]");

      CustomProduct customProduct = modelMapper.map(customProductDto,CustomProduct.class);
      Optional<CustomProduct> result = customProductRepository.findByProductId(customProduct.getProductId());

      if(result.isPresent()){
         customProductRepository.delete(result.get());
         return true;
      }

      return false;
   }

   @Override
   public List<CustomProductDto> findCustomProductByMemberId(UUID memberId) throws RuntimeException {
      log.info("[사용자 정의 상품 가져오기");
      List<CustomProduct> customProductList = customProductRepository.findByMemberId(memberId);

      List<CustomProductDto> customProductDtoList = new ArrayList<>();
      for (CustomProduct p:customProductList) {
            customProductDtoList.add(modelMapper.map(p,CustomProductDto.class));
      }
      return customProductDtoList;
   }

   @Override
   public CustomProductDto updateCustomProduct(CustomProductDto customProductDto) throws Exception {
      Optional<CustomProduct> result = customProductRepository.findByProductId(customProductDto.getProductId());
      result.orElseThrow(() -> new NoSuchElementException());

      CustomProduct customProduct = modelMapper.map(customProductDto, CustomProduct.class);
      CustomProduct savedCustomProduct = customProductRepository.save(customProduct);

      CustomProductDto savedCustomProductDto = modelMapper.map(savedCustomProduct,CustomProductDto.class);

      return savedCustomProductDto;
   }
}
