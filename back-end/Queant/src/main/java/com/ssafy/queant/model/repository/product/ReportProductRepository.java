package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.ReportProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportProductRepository extends JpaRepository<ReportProduct, Integer> {
    List<ReportProduct> findAll();

    Optional<ReportProduct> findByReportProductId(int reportProductId);
}
