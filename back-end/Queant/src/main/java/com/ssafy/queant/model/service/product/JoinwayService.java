package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.JoinwayDto;

import java.util.List;

public interface JoinwayService {
     List<JoinwayDto> findByProductId(String productId);
}
