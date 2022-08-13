package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.*;

import java.util.List;

public interface ProductService {
    List<ProductDto> findByBankId(int bankId);

    List<ProductDto> findByNameContaining(String name);

    ProductDetailDto findByProductId(int productId);

    ProductDto registProduct(ProductDto productDto);

//    ProductDto updateToProovedProduct(int productId);

    List<ProductDto> findByIsEnabledFalse();

//    void updateReportToProduct(int reportProductId, ProductDetailDto productDetail);
    void updateReportToProduct(UpdateDetailDto updateDetailDto);

    String reportStatusToUpdated(int reportProductId);
}
