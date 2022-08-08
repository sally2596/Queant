package com.ssafy.queant.controller;

import com.ssafy.queant.model.dto.member.MemberDto;
import com.ssafy.queant.model.dto.portfolio.PortfolioDto;
import com.ssafy.queant.model.dto.portfolio.PortfolioRequestDto;
import com.ssafy.queant.model.dto.portfolio.PortfolioResponseDto;
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

import java.util.ArrayList;
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
    })
    @ApiOperation(value="사용자 정의 상품 등록", notes="email, customProductDto 필수")
    @PostMapping("/custom")
    public ResponseEntity<?> registCustomProduct(@RequestBody PortfolioRequestDto portfolioRequestDto){
        log.info("[CustomProduct Regist]");

        CustomProductDto saved = portfolioService.registCustomProduct(portfolioRequestDto.getCustomProductDto(),portfolioRequestDto.getMemberId());

        // 저장된 Dto를 Response에 담아서 넘겨주어 프로덕트 키 값을 갖게 함
        return new ResponseEntity<CustomProductDto>(saved,HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="상품 삭제 성공"),
            @ApiResponse(code = 404, message="존재하는 상품이 아닙니다."),
    })
    @ApiOperation(value="사용자 정의 상품 삭제", notes="삭제하려는 customProduct의 productId 필수")
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
            @ApiResponse(code = 500, message="기타 서버 에러"),
    })
    @ApiOperation(value="포트폴리오 생성", notes="memberId, PortfolioDto 리스트 필수")
    @PostMapping
    public ResponseEntity<?> InsertPortfolio(@RequestBody PortfolioRequestDto portfolioRequestDto) {
        log.info("[Controller: InsertPortfolio]");

        try{
            portfolioService.insertPortfolio(portfolioRequestDto.getMemberId(), portfolioRequestDto.getPortfolioDtoList());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="마이 포트폴리오 가져오기 성공"),
            @ApiResponse(code = 409, message="마이포트폴리오가 존재하지 않습니다."),
            @ApiResponse(code = 500, message="기타 서버 에러"),
    })
    @ApiOperation(value="MyPortfolio 조회", notes="memberId 필수")
    @PostMapping("/posession")
    public ResponseEntity<?> MyPortfolio(@RequestBody PortfolioRequestDto portfolioRequestDto) {
        log.info("[Controller: MyPortfolio 조회]");

        try {
            PortfolioResponseDto portfolioResponseDto = portfolioService.getMyPortfolio(portfolioRequestDto.getMemberId());
            if(portfolioResponseDto.getPortfolioList()==null && portfolioResponseDto.getCustomProductList()==null){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<PortfolioResponseDto>(portfolioResponseDto, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiResponses({
            @ApiResponse(code = 200, message="가상 포트폴리오 가져오기 성공"),
            @ApiResponse(code = 404, message="존재하는 회원이 아닙니다."),
            @ApiResponse(code = 409, message="가상 포트폴리오를 보유하고 있지 않습니다."),
            @ApiResponse(code = 500, message="기타 서버 에러"),
    })
    @ApiOperation(value="가상 포트폴리오 조회", notes="memberId, portfolioCnt 필수")
    @PostMapping("/virtual")
    public ResponseEntity<?> VirtualPortfolio(@RequestBody PortfolioRequestDto portfolioRequestDto) {
        log.info("[Controller: MyPortfolio 조회]");
        List<List<PortfolioDto>> response = new ArrayList<>();
        try {
            if(portfolioRequestDto.getPortfolioCnt()<1) return new ResponseEntity<>(HttpStatus.CONFLICT);

            for(int i=1; i<=portfolioRequestDto.getPortfolioCnt(); i++){
                response.add(portfolioService.getPortfolio(portfolioRequestDto.getMemberId(), i));
            }
            return new ResponseEntity<List<List<PortfolioDto>>>(response, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiResponses({
            @ApiResponse(code = 200, message="포트폴리오 수정 성공"),
            @ApiResponse(code = 409, message="해당 포트폴리오가 존재하지 않습니다.(포트폴리오 번호 확인)"),
            @ApiResponse(code = 500, message="기타 서버 에러"),
    })
    @ApiOperation(value="포트폴리오 수정", notes="기존의 포트폴리오 중 있는데 달라졌으면 수정하고, 없으면 추가하고, 사라진것은 삭제(포트폴리오 단위). memberId, portfollioDtoList, portfolioNo 필수")
    @PutMapping()
    public ResponseEntity<?> updatePortfolio(@RequestBody PortfolioRequestDto portfolioRequestDto) {
        log.info("[Controller: UpdatePortfolio]");

        try {
            portfolioService.updatePortfolio(portfolioRequestDto.getMemberId(), portfolioRequestDto.getPortfolioDtoList(),portfolioRequestDto.getPortfolioNo());
            return new ResponseEntity<>(HttpStatus.OK);
        }  catch(NoSuchElementException e){
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.CONFLICT);
        }  catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiResponses({
            @ApiResponse(code = 200, message="포트폴리오 수정 성공"),
            @ApiResponse(code = 409, message="해당 포트폴리오가 존재하지 않습니다.(포트폴리오 번호 확인)"),
            @ApiResponse(code = 500, message="기타 서버 에러"),
    })
    @ApiOperation(value="포트폴리오 전체 삭제", notes="email, portfolioNo 필수")
    @DeleteMapping
    public ResponseEntity<?> deletePortfolio(@RequestBody PortfolioRequestDto portfolioRequestDto) {
        log.info("[Controller: DeletePortfolio]");

        try {
            portfolioService.deletePortfolio(portfolioRequestDto.getMemberId(), portfolioRequestDto.getPortfolioNo());
            return new ResponseEntity<>(HttpStatus.OK);
        }  catch(NoSuchElementException e){
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.CONFLICT);
        }  catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
