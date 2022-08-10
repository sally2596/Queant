package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.ReportProductDto;

import java.util.List;

public interface ReportProductService {
    List<ReportProductDto> findAll();

    List<ReportProductDto> findById(String memberEmail);

    void registReport(ReportProductDto reportProductDto);
}
