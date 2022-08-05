package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.ReportProductDto;
import com.ssafy.queant.model.entity.product.ReportProduct;
import com.ssafy.queant.model.repository.product.ReportProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReportProductServiceImpl implements ReportProductService {

    private final ReportProductRepository reportProductRepository;
    private final ModelMapper modelMapper;

    public ReportProductServiceImpl(ReportProductRepository reportProductRepository, ModelMapper modelMapper) {
        this.reportProductRepository = reportProductRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReportProductDto> findAll() {
        List<ReportProduct> list = reportProductRepository.findAll();
        List<ReportProductDto> reportProductDtos = new ArrayList<>();

        if (list.size() > 0) {
            for (ReportProduct r : list) {
                reportProductDtos.add(modelMapper.map(r, ReportProductDto.class));
            }
        }

        return reportProductDtos;
    }

    @Override
    public boolean updateReportToProduct(ReportProductDto reportProductDto) {


        return false;
    }

    @Override
    public boolean registReport(ReportProductDto reportProductDto) {
        return false;
    }
}
