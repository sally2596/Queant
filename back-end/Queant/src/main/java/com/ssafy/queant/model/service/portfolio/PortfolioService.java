package com.ssafy.queant.model.service.portfolio;

import com.ssafy.queant.model.dto.portfolio.PortfolioDto;
import com.ssafy.queant.model.dto.portfolio.PortfolioResponseDto;
import com.ssafy.queant.model.dto.product.CustomProductDto;

import java.util.List;
import java.util.UUID;


public interface PortfolioService {
   //사용자 정의 상품 등록
   CustomProductDto registCustomProduct(CustomProductDto customProductDto, UUID memberId) throws RuntimeException;
   //사용자 정의 상품 삭제
   public boolean deleteCustomProduct(CustomProductDto customProductDto) throws RuntimeException;
   //사용자 정의 상품 목록 조회
   List<CustomProductDto> findCustomProductByMemberId(UUID memberId) throws RuntimeException;
   //사용자 정의 상품 정보 수정
   CustomProductDto updateCustomProduct(CustomProductDto customProductDto) throws Exception;

   //포트폴리오 조회(MyPortfolio)
   PortfolioResponseDto getMyPortfolio(String email);
   //포트폴리오 조회(예상 포트폴리오)
   //포트폴리오 생성(MyPortfolio)
   PortfolioDto insertPortfolio(String email, PortfolioDto portfolioDto, String productId) throws Exception;
   //포트폴리오 생성(예상 포트폴리오)
   //포트폴리오 수정(MyPortfolio 상품 추가 및 제거)
   //포트폴리오 수정(예상 포트폴리오 상품 추가 및 제거)
   //포트폴리오 삭제(예상 포트폴리오)
}
