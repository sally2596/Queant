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
   PortfolioResponseDto getMyPortfolio(String email) throws Exception;
   //포트폴리오 조회(예상 포트폴리오)
   List<PortfolioDto> getPortfolio(UUID memberId, int cnt) throws Exception;
   //포트폴리오 생성 모두 새로움
   void insertPortfolio(String email, List<PortfolioDto> portfolioDtoList) throws Exception;
   //포트폴리오 수정(예상 포트폴리오 상품 추가 및 제거) 있는데 달라졌으면 수정하고, 없으면 추가하고, 사라진것은 삭제(포트폴리오 단위로 시행)
   void updatePortfolio(String email, List<PortfolioDto> portfolioDtoList, int portfolioNo) throws Exception;
   //포트폴리오 삭제(예상 포트폴리오) 통으로 하나 삭제
   void deletePortfolio(String email, int portfolioNo) throws Exception;
   //포트폴리오 비교... 와 암담하다
}
