package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.TraitSetDto;

import java.util.List;

public interface TraitSetService {
    List<TraitSetDto> findByProductId(int productId);
}
