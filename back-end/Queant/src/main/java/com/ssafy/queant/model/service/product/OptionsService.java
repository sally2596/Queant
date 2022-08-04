package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.OptionsDto;

import java.util.List;

public interface OptionsService {
    List<OptionsDto> findByProductId(int productId);
}
