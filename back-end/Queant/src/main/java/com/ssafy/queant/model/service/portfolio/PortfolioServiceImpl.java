package com.ssafy.queant.model.service.portfolio;

import com.ssafy.queant.model.dto.portfolio.PortfolioDto;
import com.ssafy.queant.model.dto.portfolio.PortfolioResponseDto;
import com.ssafy.queant.model.dto.product.CustomProductDto;

import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.portfolio.Portfolio;
import com.ssafy.queant.model.entity.product.CustomProduct;
import com.ssafy.queant.model.entity.product.Product;
import com.ssafy.queant.model.repository.MemberRepository;
import com.ssafy.queant.model.repository.PortfolioRepository;
import com.ssafy.queant.model.repository.product.CustomProductRepository;
import com.ssafy.queant.model.repository.product.ProductRepository;

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
      Optional<List<CustomProduct>> result = customProductRepository.findByMemberId(memberId);
      result.orElseThrow(() -> new NoSuchElementException());

      List<CustomProductDto> customProductDtoList = new ArrayList<>();
      for (CustomProduct p:result.get()) {
            customProductDtoList.add(modelMapper.map(p,CustomProductDto.class));
      }
      return customProductDtoList;
   }

   @Override
   public CustomProductDto updateCustomProduct(CustomProductDto customProductDto) throws Exception {
      Optional<CustomProduct> result = customProductRepository.findByProductId(customProductDto.getProductId());
      result.orElseThrow(() -> new NoSuchElementException());

      CustomProduct customProduct = modelMapper.map(customProductDto, CustomProduct.class);

      customProduct.setMemberId(result.get().getMemberId());
      CustomProduct savedCustomProduct = customProductRepository.save(customProduct);

      CustomProductDto savedCustomProductDto = modelMapper.map(savedCustomProduct,CustomProductDto.class);

      return savedCustomProductDto;
   }

//   @Override
//   public PortfolioResponseDto getMyPortfolio(String email) throws Exception {
//      log.info("[getMyPortfolio] : email: {}", email);
//      Optional<Member> result = memberRepository.findByEmail(email);
//      result.orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
//      Member member = result.get();
//
//      PortfolioResponseDto portfolioResponseDto = new PortfolioResponseDto();
//      //사용자 정의상품 찾기
//      try{
//
//         List<CustomProductDto> customProductDtoList = findCustomProductByMemberId(member.getMemberId());
//
//         portfolioResponseDto.setCustomProductList(customProductDtoList);
//      } catch(Exception e){
//         e.printStackTrace();
//      }
//
//      //0번 포트폴리오 찾기
//      try {
//
//         List<PortfolioDto> myPortfolioList = getPortfolio(member.getMemberId(), 0);
//
//         portfolioResponseDto.setPortfolioList(myPortfolioList);
//      } catch(Exception e) {
//         e.printStackTrace();
//      }
//
//
//      return portfolioResponseDto;
//   }

//   @Override
//   public List<PortfolioDto> getPortfolio(UUID memberId, int portfolioNo) throws Exception {
//      List<PortfolioDto> response = new ArrayList<>();
//
//      Optional<List<Portfolio>> result = portfolioRepository.findByMemberMemberIdAndPortfolioNo(memberId, portfolioNo);
//      result.orElseThrow(() -> new NoSuchElementException("해당 포트폴리오가 없습니다."));
//
//      result.get().forEach(portfolio -> response.add(modelMapper.map(portfolio, PortfolioDto.class)));
//      return response;
//   }

//   @Override
//   public void insertPortfolio(String email, List<PortfolioDto> portfolioDtoList) throws Exception{
//
//      log.info("[insertPortfolio] : email: {} 포트폴리오 추가", email);
//      Optional<Member> result = memberRepository.findByEmail(email);
//      result.orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
//      Member member = result.get();
//
//      for(PortfolioDto portfolioDto : portfolioDtoList){
//         Optional<Product> product = productRepository.findByProductId(portfolioDto.getProductId());
//         product.orElseThrow(() -> new NoSuchElementException());
//
//         Portfolio portfolio = modelMapper.map(portfolioDto, Portfolio.class);
//         //portfolio.setMember(member);
//         portfolio.setProduct(product.get());
//
//         portfolioRepository.save(portfolio);
//
//      }
//   }
//
//   //포트폴리오 수정(예상 포트폴리오 상품 추가 및 제거)
//   @Override
//   public void updatePortfolio(String email, List<PortfolioDto> portfolioDtoList, int portfolioNo) throws Exception {
//      //유저 찾기
//      log.info("[updatePortfolio] : email: {} 포트폴리오 수정, 포트폴리오 번호: {}", email, portfolioNo);
//      Optional<Member> result = memberRepository.findByEmail(email);
//      result.orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
//      Member member = result.get();
//
//      //기존의 포트폴리오 가져오기
//      Optional<List<Portfolio>> portfolioResult = portfolioRepository.findByMemberMemberIdAndPortfolioNo(member.getMemberId(),portfolioNo);
//      result.orElseThrow(() -> new NoSuchElementException());
//
//      HashMap<Integer,Integer> map = new HashMap<>();
//      List<Portfolio> existing = portfolioResult.get();
//
//      for(int i=0; i<existing.size(); i++){
//         map.put(existing.get(i).getPortfolioId(),i);
//      }
//
//      for(PortfolioDto portfolioDto: portfolioDtoList){
//         //기존에 있던 포트폴리오 수정
//         if(map.containsKey(portfolioDto.getPortfolioId())){
//            Portfolio portfolio = existing.get(map.get(portfolioDto.getPortfolioId()));
//            portfolio.setAmount(portfolioDto.getAmount());
//            portfolio.setStartDate(portfolioDto.getStartDate());
//            portfolio.setEndDate(portfolioDto.getEndDate());
//            portfolio.setAmountFixed(portfolioDto.getAmountFixed());
//
//            portfolioRepository.save(portfolio);
//            map.remove(portfolioDto.getPortfolioId());
//         } else { //기존에 없다면 추가
//            Optional<Product> product = productRepository.findByProductId(portfolioDto.getProductId());
//            product.orElseThrow(() -> new NoSuchFieldException());//해당 상품이 없음
//
//            Portfolio portfolio = modelMapper.map(portfolioDto, Portfolio.class);
//            //portfolio.setMember(member);
//            portfolio.setProduct(product.get());
//
//            portfolioRepository.save(portfolio);
//         }
//      }
//      //set에 남아있다면 삭제
//      for(int key : map.keySet()){
//         portfolioRepository.deleteById(key);
//      }
//   }
//
//   @Override
//   public void deletePortfolio(String email, int portfolioNo) throws Exception {
//      //유저 찾기
//      log.info("[updatePortfolio] : email: {} 포트폴리오 수정, 포트폴리오 번호: {}", email, portfolioNo);
//      Optional<Member> result = memberRepository.findByEmail(email);
//      result.orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
//      Member member = result.get();
//
//
//      Optional<List<Portfolio>> portfolioResult = portfolioRepository.findByMemberMemberIdAndPortfolioNo(member.getMemberId(), portfolioNo);
//      portfolioResult.orElseThrow(()-> new NoSuchElementException());
//
//      portfolioRepository.deleteAll(portfolioResult.get());
//
//   }
}
