package com.ssafy.queant.model.entity.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(nullable = false) // 은행이나 기관 이름
    private String institutionName;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private boolean isDeposit;

    @Column(nullable = false)
    private float baseRate;

    @Column(nullable = false) // 우대금리 -> 없다면 디폴트 0
    @Builder.Default
    private float specialRate =0L;

    private boolean isFixedRsrv;

    private String etc; // 기타 사항

    private Long amount; // 예금일때 한번에 넣을 금액
    private Long amountRegular; // 적금일때 한달에 넣을 금액

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID memberId;
}
