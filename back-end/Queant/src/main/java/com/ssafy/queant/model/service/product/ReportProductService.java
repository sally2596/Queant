package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.ReportProductDto;

import java.util.List;

public interface ReportProductService {
    List<ReportProductDto> findAll();

    void updateReportToProduct(int reportProductId);

    void registReport(ReportProductDto reportProductDto);
}
