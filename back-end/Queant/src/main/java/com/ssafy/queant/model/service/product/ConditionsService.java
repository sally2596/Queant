package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.ConditionsDto;

import java.util.List;

public interface ConditionsService {
    List<ConditionsDto> findByProductId(int productId);
}
