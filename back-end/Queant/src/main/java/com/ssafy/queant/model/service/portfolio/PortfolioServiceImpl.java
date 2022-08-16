package com.ssafy.queant.model.service.portfolio;

import com.ssafy.queant.model.dto.portfolio.PortfolioDto;
import com.ssafy.queant.model.dto.portfolio.PortfolioResponseDto;
import com.ssafy.queant.model.dto.product.ConditionsDto;
import com.ssafy.queant.model.dto.product.CustomProductDto;

import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.portfolio.Portfolio;
import com.ssafy.queant.model.entity.product.Conditions;
import com.ssafy.queant.model.entity.product.CustomProduct;
import com.ssafy.queant.model.entity.product.Options;
import com.ssafy.queant.model.entity.product.Product;
import com.ssafy.queant.model.repository.MemberRepository;
import com.ssafy.queant.model.repository.portfolio.PortfolioRepository;
import com.ssafy.queant.model.repository.product.CustomProductRepository;
import com.ssafy.queant.model.repository.product.ProductRepository;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
   @Transactional
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
   @Transactional
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
   @Transactional
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
   @Transactional
   public CustomProductDto updateCustomProduct(CustomProductDto customProductDto) throws Exception {
      Optional<CustomProduct> result = customProductRepository.findByProductId(customProductDto.getProductId());
      result.orElseThrow(() -> new NoSuchElementException());

      CustomProduct customProduct = modelMapper.map(customProductDto, CustomProduct.class);

      customProduct.setMemberId(result.get().getMemberId());
      CustomProduct savedCustomProduct = customProductRepository.save(customProduct);

      CustomProductDto savedCustomProductDto = modelMapper.map(savedCustomProduct,CustomProductDto.class);

      return savedCustomProductDto;
   }

   @Override
   @Transactional
   public PortfolioResponseDto getMyPortfolio(UUID memberId) throws Exception {
      log.info("[getMyPortfolio] : memberId: {}", memberId);
      PortfolioResponseDto portfolioResponseDto = new PortfolioResponseDto();
      //사용자 정의상품 찾기
      try{
         List<CustomProductDto> customProductDtoList = findCustomProductByMemberId(memberId);
         portfolioResponseDto.setCustomProductList(customProductDtoList);
      } catch(Exception e){
         e.printStackTrace();
      }

      //0번 포트폴리오 찾기

      try{
        List<PortfolioDto> portfolioDtoList = getPortfolio(memberId,0);
        portfolioResponseDto.setPortfolioList(portfolioDtoList);
      } catch(Exception e){
         e.printStackTrace();
      }

      return portfolioResponseDto;
   }

   @Override
   @Transactional
   public List<PortfolioDto> getPortfolio(UUID memberId, int portfolioNo) throws Exception {
      List<PortfolioDto> response = new ArrayList<>();

      Member member = Member.builder().memberId(memberId).build();
      Optional<List<Portfolio>> result = portfolioRepository.findPortfolioByMemberAndPortfolioNo(member,portfolioNo);
      result.orElseThrow(() -> new NoSuchElementException("해당 포트폴리오가 없습니다."));

      result.get().forEach(portfolio -> response.add(modelMapper.map(portfolio, PortfolioDto.class)));

      for(PortfolioDto portfolioDto : response){
         portfolioDto.addConditionIds();
      }
      return response;
   }

   @Override
   @Transactional
   public void insertPortfolio(UUID memberId, List<PortfolioDto> portfolioDtoList) throws Exception{

      log.info("[insertPortfolio] : memberId: {} 포트폴리오 추가", memberId);
      int portfolioIdx = 0;
      for(PortfolioDto portfolioDto : portfolioDtoList){
         portfolioIdx = portfolioDto.getPortfolioNo();

         Product product = Product.builder().productId(portfolioDto.getProductId()).build();
         Options option = Options.builder().optionId(portfolioDto.getOptionId()).build();
         Member member = Member.builder().memberId(memberId).build();
         //포트폴리오 생성
         Portfolio portfolio = Portfolio.builder()
                 .member(member)
                 .product(product)
                 .portfolioNo(portfolioDto.getPortfolioNo())
                 .amount(portfolioDto.getAmount())
                 .startDate(portfolioDto.getStartDate())
                 .endDate(portfolioDto.getEndDate())
                 .option(option)
                 .build();

         //컨디션 생성 및 주입
         for(int conditionId : portfolioDto.getConditionIds()){
            Conditions condition = Conditions.builder().conditionId(conditionId).build();
            portfolio.addCondition(condition);
         }

         portfolioRepository.save(portfolio);
      }

      Member member = memberRepository.findById(memberId).get();
      member.setPortfolio_cnt(portfolioIdx);
      memberRepository.save(member);
   }

   //포트폴리오 수정(예상 포트폴리오 상품 추가 및 제거)
//   @Override
//   @Transactional
//   public void updatePortfolio(UUID memberId, List<PortfolioDto> portfolioDtoList, int portfolioNo) throws Exception {
//      //기존의 포트폴리오 가져오기
//      Member member = Member.builder().memberId(memberId).build();
//      Optional<List<Portfolio>> portfolioResult = portfolioRepository.findPortfolioByMemberAndPortfolioNo(member,portfolioNo);
//      portfolioResult.orElseThrow(() -> new NoSuchElementException());
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
//            //기존 -> portfolio 새거-> portfolioDto
//            Portfolio portfolio = existing.get(map.get(portfolioDto.getPortfolioId()));
//            portfolio.setAmount(portfolioDto.getAmount());
//            portfolio.setStartDate(portfolioDto.getStartDate());
//            portfolio.setEndDate(portfolioDto.getEndDate());
//
//            //옵션 수정
//            if(portfolio.getOption().getOptionId() != portfolioDto.getOptionId()){
//               portfolio.setOption(Options.builder().optionId(portfolioDto.getOptionId()).build());
//            }
//
//            //컨디션 수정
//            HashSet<Integer> conds = new HashSet<>();
//            Set<Conditions> changeConditions = new HashSet<>();
//            for(Conditions conditions : portfolio.getConditions()){
//               conds.add(conditions.getConditionId());
//            }
//
//            for(int cond : portfolioDto.getConditionIds()){
//               //이미 있으면 비교 set 에서 제거
//               if(conds.contains(cond)){
//                  Conditions condition = Conditions.builder().conditionId(cond).build();
//                  changeConditions.add(condition);
//                  conds.remove(cond);
//               } else { //없으면 추가해주기
//                  Conditions condition = Conditions.builder().conditionId(cond).build();
//                  changeConditions.add(condition);
//               }
//            }
//
//            portfolio.setConditions(changeConditions);
//
//            portfolioRepository.save(portfolio);
//            map.remove(portfolioDto.getPortfolioId());
//         } else { //기존에 없다면 추가
//            Product product = Product.builder().productId(portfolioDto.getProductId()).build();
//            Options option = Options.builder().optionId(portfolioDto.getOptionId()).build();
//
//            //포트폴리오 생성
//            Portfolio portfolio = Portfolio.builder()
//                    .member(member)
//                    .product(product)
//                    .portfolioNo(portfolioDto.getPortfolioNo())
//                    .amount(portfolioDto.getAmount())
//                    .startDate(portfolioDto.getStartDate())
//                    .endDate(portfolioDto.getEndDate())
//                    .option(option)
//                    .build();
//
//            //컨디션 생성 및 주입
//            for(int conditionId : portfolioDto.getConditionIds()){
//               Conditions condition = Conditions.builder().conditionId(conditionId).build();
//               portfolio.addCondition(condition);
//            }
//
//            portfolioRepository.save(portfolio);
//         }
//      }
//      //set에 남아있다면 삭제
//      for(int key : map.keySet()){
//         portfolioRepository.deleteById(key);
//      }
//   }

//   @Override
//   @Transactional
//   public void deletePortfolio(UUID memberId, int portfolioNo) throws Exception {
//
//      log.info("[updatePortfolio] : memberId: {} 포트폴리오 수정, 포트폴리오 번호: {}", memberId, portfolioNo);
//
//      Optional<Member> result = memberRepository.findById(memberId);
//      Member member = result.get();
//
//      Optional<List<Portfolio>> portfolioResult = portfolioRepository.findPortfolioByMemberAndPortfolioNo(member,portfolioNo);
//      portfolioResult.orElseThrow(()-> new NoSuchElementException());
//
//      portfolioRepository.deleteAll(portfolioResult.get());
//
//      for(int i=portfolioNo+1; i<member.getPortfolio_cnt(); i++){
//         Optional<List<Portfolio>> portfolios = portfolioRepository.findPortfolioByMemberAndPortfolioNo(member, i);
//
//         for(Portfolio portfolio : portfolios.get()){
//            portfolio.setPortfolioNo(i-1);
//            portfolioRepository.save(portfolio);
//         }
//      }
//
//      member.setPortfolio_cnt(member.getPortfolio_cnt()-1);
//
//   }

   @Override
   @Transactional
   public void updatePortfolioSingle(PortfolioDto portfolioDto) throws Exception {
      Optional<Portfolio> result = portfolioRepository.findById(portfolioDto.getPortfolioId());
      result.orElseThrow(() -> new NoSuchElementException());
      Portfolio portfolio = result.get();

      portfolio.setAmount(portfolioDto.getAmount());
      portfolio.setStartDate(portfolioDto.getStartDate());
      portfolio.setEndDate(portfolioDto.getEndDate());

      //옵션 수정
      if(portfolio.getOption().getOptionId() != portfolioDto.getOptionId()){
         portfolio.setOption(Options.builder().optionId(portfolioDto.getOptionId()).build());
      }

      //컨디션 수정
      HashSet<Integer> conds = new HashSet<>();
      Set<Conditions> changeConditions = new HashSet<>();
      for(Conditions conditions : portfolio.getConditions()){
         conds.add(conditions.getConditionId());
      }

      for(int cond : portfolioDto.getConditionIds()){
         //이미 있으면 비교 set 에서 제거
         if(conds.contains(cond)){
            Conditions condition = Conditions.builder().conditionId(cond).build();
            changeConditions.add(condition);
            conds.remove(cond);
         } else { //없으면 추가해주기
            Conditions condition = Conditions.builder().conditionId(cond).build();
            changeConditions.add(condition);
         }
      }

      portfolio.setConditions(changeConditions);

      portfolioRepository.save(portfolio);
   }

   @Override
   @Transactional
   public void deletePortfolioSingle(int portfolioId) throws Exception {
      Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
      portfolio.orElseThrow(() -> new NoSuchElementException());
      portfolioRepository.delete(portfolio.get());
   }

   @Override
   @Transactional
   public void deleteAndInsert(UUID memberId, List<PortfolioDto> portfolioDtoList) throws Exception {
      Member member = Member.builder().memberId(memberId).build();
      Optional<List<Portfolio>> portfolio = portfolioRepository.findPortfolioByMember(member);

      if(portfolio.isPresent()){
         for(Portfolio port: portfolio.get()){
            if(port.getPortfolioNo() == 0) continue;
            log.info("[deleteAdnInsert] 삭제되는 포트폴리오 : {}", port.toString());
            portfolioRepository.delete(port);

         }
      }

      portfolioRepository.flush();

      insertPortfolio(memberId, portfolioDtoList);
   }

}
