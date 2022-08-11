package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.ReportProductDto;
import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.product.Options;
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
    public List<ReportProductDto> findById(String memberEmail) {

        Optional<Member> result = memberRepository.findByEmail(memberEmail);
        Member member = result.get();

        List<ReportProduct> list = reportProductRepository.findByMemberId(member.getMemberId());
        List<ReportProductDto> reportProductDtos = new ArrayList<>();

        if (list.size() > 0) {
            for (ReportProduct r : list) {
                reportProductDtos.add(modelMapper.map(r, ReportProductDto.class));
            }
        }
        return reportProductDtos;
    }

    @Override
    public ReportProductDto findByReportId(int reportId) {

        Optional<ReportProduct> result =
                reportProductRepository.findByReportProductId(reportId);

        result.orElseThrow(() -> new NoSuchElementException("잘못된 제보 번호입니다."));

        ReportProduct reportProduct = result.get();

        ReportProductDto reportProductDto = modelMapper.map(reportProduct, ReportProductDto.class);

        return reportProductDto;
    }

    @Override
    public void registReport(ReportProductDto reportProductDto) {
        Optional<Member> result = memberRepository.findByEmail(reportProductDto.getMemberEmail());
        result.orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));

        ReportProduct reportProduct = modelMapper.map(reportProductDto, ReportProduct.class);
        reportProduct.setMember_id(result.get().getMemberId());
        reportProductRepository.save(reportProduct);
    }
}
