package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.product.CustomProductDto;

import java.util.List;


public interface PortfolioService {
   boolean registCustomProduct(CustomProductDto customProductDto) throws RuntimeException;
   List<CustomProductDto> findCustomProductByMemberEmail(String email) throws RuntimeException;
}
