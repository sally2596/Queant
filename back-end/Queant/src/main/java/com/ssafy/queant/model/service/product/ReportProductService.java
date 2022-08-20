package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.ReportProductDto;

import java.util.List;
import java.util.UUID;

public interface ReportProductService {
    List<ReportProductDto> findAll();

    List<ReportProductDto> findById(UUID uuid);

    ReportProductDto findByReportId(int reportId);

    void registReport(ReportProductDto reportProductDto);
}
