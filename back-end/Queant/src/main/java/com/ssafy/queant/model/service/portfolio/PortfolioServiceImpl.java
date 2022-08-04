package com.ssafy.queant.model.service.portfolio;

import com.ssafy.queant.model.dto.portfolio.PortfolioDto;
import com.ssafy.queant.model.dto.portfolio.PortfolioResponseDto;
import com.ssafy.queant.model.dto.product.CustomProductDto;
import com.ssafy.queant.model.dto.product.ProductDto;
import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.portfolio.Portfolio;
import com.ssafy.queant.model.entity.product.CustomProduct;
import com.ssafy.queant.model.entity.product.Product;
import com.ssafy.queant.model.repository.MemberRepository;
import com.ssafy.queant.model.repository.PortfolioRepository;
import com.ssafy.queant.model.repository.product.CustomProductRepository;
import com.ssafy.queant.model.repository.product.ProductRepository;
import com.ssafy.queant.model.service.portfolio.PortfolioService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {

   private final CustomProductRepository customProductRepository;
   private final ModelMapper modelMapper;

   private final ProductRepository productRepository;

   private final MemberRepository memberRepository;

   private final PortfolioRepository portfolioRepository;

   @Autowired
   public PortfolioServiceImpl(
              CustomProductRepository customProductRepository,
              ModelMapper modelMapper,
              ProductRepository productRepository,
              MemberRepository memberRepository,
              PortfolioRepository portfolioRepository
           ) {
      this.customProductRepository = customProductRepository;
      this.modelMapper = modelMapper;
      this.productRepository = productRepository;
      this.memberRepository = memberRepository;
      this.portfolioRepository = portfolioRepository;
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

   @Override
   public PortfolioResponseDto getMyPortfolio(String email) {
      return null;
   }

   @Override
   public PortfolioDto insertPortfolio(String email, PortfolioDto portfolioDto, String productId) throws Exception{

      log.info("[insertPortfolio] : email: {}, productId : {}", email, productId);
      Optional<Member> result = memberRepository.findByEmail(email);
      result.orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
      Member member = result.get();

      Optional<Product> product = productRepository.findByProductId(productId);
      product.orElseThrow(() -> new NoSuchElementException());

      Portfolio portfolio = modelMapper.map(portfolioDto, Portfolio.class);
      portfolio.setMember(member);
      portfolio.setProduct(product.get());

      Portfolio savedPortfolio = portfolioRepository.save(portfolio);
      PortfolioDto savedPortfolioDto = modelMapper.map(savedPortfolio, PortfolioDto.class);
      savedPortfolioDto.setProduct(modelMapper.map(savedPortfolioDto.getProduct(), ProductDto.class));

      return savedPortfolioDto;
   }
}
