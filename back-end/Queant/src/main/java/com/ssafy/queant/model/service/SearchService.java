package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.Search.SearchKeywordDto;
import com.ssafy.queant.model.dto.Search.SearchRequestDto;
import com.ssafy.queant.model.dto.product.SearchResponseDto;

public interface SearchService {
    SearchKeywordDto getSearchKeyword();

    SearchResponseDto searchSingle(SearchRequestDto searchRequestDto, boolean isDeposit, int page);
}
