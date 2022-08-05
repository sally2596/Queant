package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.ReportProductDto;
import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.product.ReportProduct;
import com.ssafy.queant.model.repository.MemberRepository;
import com.ssafy.queant.model.repository.product.ReportProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class ReportProductServiceImpl implements ReportProductService {

    private final ReportProductRepository reportProductRepository;
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    public ReportProductServiceImpl(ReportProductRepository reportProductRepository, ModelMapper modelMapper, MemberRepository memberRepository) {
        this.reportProductRepository = reportProductRepository;
        this.modelMapper = modelMapper;
        this.memberRepository = memberRepository;
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
    public void updateReportToProduct(int reportProductId) {
        Optional<ReportProduct> result =
                reportProductRepository.findByReportProductId(reportProductId);

        result.orElseThrow(() -> new NoSuchElementException("없는 제보 정보입니다."));

        ReportProduct reportProduct = result.get();
        reportProduct.setUpdated(true);
        ReportProduct saved = reportProductRepository.save(reportProduct);
    }

    @Override
    public void registReport(ReportProductDto reportProductDto) {
        Optional<Member> result = memberRepository.findByEmail(reportProductDto.getMemberEmail());
        result.orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));

        ReportProduct reportProduct = modelMapper.map(reportProductDto, ReportProduct.class);
        reportProductRepository.save(reportProduct);
    }
}
