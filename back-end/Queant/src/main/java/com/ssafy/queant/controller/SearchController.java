package com.ssafy.queant.controller;

import com.ssafy.queant.model.dto.Search.SearchKeywordDto;
import com.ssafy.queant.model.dto.Search.SearchRequestDto;
import com.ssafy.queant.model.dto.product.ProductDto;
import com.ssafy.queant.model.service.SearchService;
import com.ssafy.queant.model.service.product.ProductService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;
    private final ProductService productService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "검색 키워드 목록 가져오기 성공"),
    })
    @Operation(summary = "검색 키워드 정보", description = "가입방법, 은행타입, 은행, 우대조건 제공")
    @GetMapping("/keyword")
    public ResponseEntity<?> getSearchKeyword() throws Exception {
        SearchKeywordDto keywordDto = searchService.getSearchKeyword();
        return new ResponseEntity<>(keywordDto, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "검색 결과 가져오기 성공"),
            @ApiResponse(code = 404, message = "검색 정보가 존재하지 않습니다."),
    })
    @Operation(summary = "텍스트로 상품 찾기", description = "Nav 바에서 검색할 경우 상품 보여주기")
    @GetMapping()
    public ResponseEntity<?> getProductByName(
            @RequestParam(value = "keyword") String keyword
    ) {
        List<ProductDto> list = productService.findByNameContaining(keyword);
        if (list.size() > 0)
            return new ResponseEntity<>(list, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "예금 단품 검색 성공"),
            @ApiResponse(code = 404, message = "검색 정보가 존재하지 않습니다."),
    })
    @Operation(summary = "예금 단품 검색", description = "키워드로 예금 단품 검색")
    @PostMapping(value = "/deposit/single")
    public ResponseEntity<?> getDepositSingle(@RequestBody SearchRequestDto searchRequestDto) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "적금 단품 검색 성공"),
            @ApiResponse(code = 404, message = "검색 정보가 존재하지 않습니다."),
    })
    @Operation(summary = "적금 단품 검색", description = "키워드로 적금 단품 검색")
    @PostMapping(value = "/saving/single")
    public ResponseEntity<?> getSavingSingle(@RequestBody SearchRequestDto searchRequestDto) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ApiResponses({
            @ApiResponse(code = 200, message = "적금 세트 검색 성공"),
            @ApiResponse(code = 404, message = "검색 정보가 존재하지 않습니다."),
    })
    @Operation(summary = "적금 세트 검색", description = "키워드로 적금 세트 검색")
    @PostMapping(value = "/saving/set")
    public ResponseEntity<?> getSavingSet(@RequestBody SearchRequestDto searchRequestDto) {


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
