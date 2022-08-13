package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.ReportProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReportProductRepository extends JpaRepository<ReportProduct, Integer> {
    List<ReportProduct> findAll();

    Optional<ReportProduct> findByReportProductId(int reportProductId);

    @Query(value = "select * from report_product where member_id=:memberId", nativeQuery = true)
    List<ReportProduct> findByMemberId(UUID memberId);
}
