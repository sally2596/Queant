package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.product.CustomProductDto;

import java.util.List;
import java.util.UUID;


public interface PortfolioService {
   CustomProductDto registCustomProduct(CustomProductDto customProductDto, UUID memberId) throws RuntimeException;
   public boolean deleteCustomProduct(CustomProductDto customProductDto) throws RuntimeException;
   List<CustomProductDto> findCustomProductByMemberId(UUID memberId) throws RuntimeException;
}
