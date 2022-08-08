package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.Search.SearchKeywordDto;
import com.ssafy.queant.model.dto.Search.SearchRequestDto;
import com.ssafy.queant.model.dto.product.ProductDto;

import java.util.List;

public interface SearchService {
    SearchKeywordDto getSearchKeyword();

    List<ProductDto> searchSingle(SearchRequestDto searchRequestDto, boolean isDeposit, int page);
}
