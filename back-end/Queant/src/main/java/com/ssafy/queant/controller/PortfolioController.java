package com.ssafy.queant.controller;

import com.ssafy.queant.model.dto.member.MemberDto;
import com.ssafy.queant.model.dto.product.CustomProductDto;
import com.ssafy.queant.model.service.MemberService;
import com.ssafy.queant.model.service.PortfolioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/regist/custom")
    public ResponseEntity<?> registCustomProduct(@RequestBody CustomProductDto customProductDto,@RequestBody String email){
        log.info("[CustomProduct Regist]");

        MemberDto memberDto = memberService.findMember(email);

        if(memberDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        CustomProductDto saved = portfolioService.registCustomProduct(customProductDto,memberDto.getMemberId());

        // 저장된 Dto를 Response에 담아서 넘겨주어 프로덕트 키 값을 갖게 함
        return new ResponseEntity<CustomProductDto>(saved,HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="상품 등록 성공"),
            @ApiResponse(code = 404, message="존재하는 상품이 아닙니다."),
    })
    @ApiOperation(value="사용자 정의 상품 등록", notes="email 필수")
    @PostMapping("/delete/custom")
    public ResponseEntity<?> deleteCustomProduct(@RequestBody CustomProductDto customProductDto){
        log.info("[CustomProduct Delete]");

        boolean isDeleted = portfolioService.deleteCustomProduct(customProductDto);

        if(isDeleted)
            return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="커스텀 상품 가져오기 성공"),
            @ApiResponse(code = 404, message="존재하는 회원이 아닙니다."),
    })
    @ApiOperation(value="사용자 정의 상품 등록", notes="email 필수")
    @GetMapping("/product/custom")
    public ResponseEntity<?> getCustomProduct(@RequestBody CustomProductDto customProductDto,@RequestBody String email){
        log.info("[Get CustomProductList]");

        MemberDto memberDto = memberService.findMember(email);

        if(memberDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<CustomProductDto> list = portfolioService.findCustomProductByMemberId(memberDto.getMemberId());

        return new ResponseEntity<List<CustomProductDto>>(list,HttpStatus.OK);
    }

}
