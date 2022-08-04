package com.ssafy.queant.controller;

import com.ssafy.queant.model.dto.member.MemberDto;
import com.ssafy.queant.model.dto.portfolio.PortfolioDto;
import com.ssafy.queant.model.dto.portfolio.PortfolioRequestDto;
import com.ssafy.queant.model.dto.product.CustomProductDto;
import com.ssafy.queant.model.service.member.MemberService;
import com.ssafy.queant.model.service.portfolio.PortfolioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final MemberService memberService;

    @ApiResponses({
            @ApiResponse(code = 200, message="상품 등록 성공"),
            @ApiResponse(code = 404, message="존재하는 회원이 아닙니다."),
    })
    @ApiOperation(value="사용자 정의 상품 등록", notes="email 필수")
    @PostMapping("/custom")
    public ResponseEntity<?> registCustomProduct(@RequestBody CustomProductDto customProductDto,@RequestBody String email){
        log.info("[CustomProduct Regist]");

        MemberDto memberDto = memberService.findMember(email);

        if(memberDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        CustomProductDto saved = portfolioService.registCustomProduct(customProductDto,memberDto.getMemberId());

        // 저장된 Dto를 Response에 담아서 넘겨주어 프로덕트 키 값을 갖게 함
        return new ResponseEntity<CustomProductDto>(saved,HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="상품 삭제 성공"),
            @ApiResponse(code = 404, message="존재하는 상품이 아닙니다."),
    })
    @ApiOperation(value="사용자 정의 상품 삭제", notes="email 필수")
    @DeleteMapping("/custom")
    public ResponseEntity<?> deleteCustomProduct(@RequestBody CustomProductDto customProductDto){
        log.info("[CustomProduct Delete]");

        boolean isDeleted = portfolioService.deleteCustomProduct(customProductDto);

        if(isDeleted)
            return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="해당 회원의 사용자정의 상품 가져오기 성공"),
            @ApiResponse(code = 404, message="존재하는 회원이 아닙니다."),
    })
    @ApiOperation(value="사용자 정의 상품 조회", notes="email 필수")
    @PostMapping("/customlist")
    public ResponseEntity<?> getCustomProduct(@RequestBody String email){
        log.info("[Get CustomProductList]");

        MemberDto memberDto = memberService.findMember(email);

        if(memberDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<CustomProductDto> list = portfolioService.findCustomProductByMemberId(memberDto.getMemberId());

        return new ResponseEntity<List<CustomProductDto>>(list,HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="사용자 정의 상품 수정 성공"),
            @ApiResponse(code = 404, message="존재하는 상품이 아닙니다."),
    })
    @ApiOperation(value="사용자 정의 상품 수정", notes="customProduct의 기존데이터와 수정된데이터 모두 보내주세요")
    @PutMapping("/custom")
    public ResponseEntity<?> updateCustomProduct(@RequestBody CustomProductDto customProductDto){
        log.info("[Update CustomProductList]");
        CustomProductDto savedCustomProduct;
        try {
            savedCustomProduct = portfolioService.updateCustomProduct(customProductDto);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CustomProductDto>(savedCustomProduct,HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="포트폴리오 생성 성공"),
            @ApiResponse(code = 404, message="존재하는 회원이 아닙니다."),
            @ApiResponse(code = 409, message="존재하는 상품이 아닙니다."),
            @ApiResponse(code = 500, message="기타 서버 에러"),
    })
    @ApiOperation(value="포트폴리오 생성", notes="유저 email과 Portfolio 리스트 필수")
    @PostMapping
    public ResponseEntity<?> InsertPortfolio(@RequestBody PortfolioRequestDto portfolioRequestDto) {
        log.info("[Controller: InsertPortfolio]");

        try{
            portfolioService.insertPortfolio(portfolioRequestDto.getEmail(), portfolioRequestDto.getPortfolioDtoList());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UsernameNotFoundException ue) {
            ue.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(NoSuchElementException ne){
            ne.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
