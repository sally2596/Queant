package com.ssafy.queant.controller;

import com.ssafy.queant.model.dto.Search.SearchKeywordDto;
import com.ssafy.queant.model.service.SearchService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    @ApiResponses({
            @ApiResponse(code = 200, message="검색 키워드 목록 가져오기 성공"),
    })
    @Operation(summary = "검색 키워드 정보", description = "가입방법, 은행타입, 은행, 우대조건 제공")
    @GetMapping("/keyword")
    //200은 성공 409중복되었다.
    public ResponseEntity<?> getSearchKeyword() throws Exception {
        SearchKeywordDto keywordDto = searchService.getSearchKeyword();
        return new ResponseEntity<>(keywordDto,HttpStatus.OK);
    }

}
