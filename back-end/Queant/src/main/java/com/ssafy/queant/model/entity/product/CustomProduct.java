package com.ssafy.queant.model.entity.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private int product_id;

    @Column(nullable = false) // 은행이나 기관 이름
    private String institution_name;

    @Column(nullable = false)
    private String product_name;

    @Column(nullable = false)
    private boolean is_deposit;

    @Column(nullable = false)
    private double base_rate;

    @Column(nullable = false) // 우대금리 -> 없다면 디폴트 0
    @Builder.Default
    private double special_rate =0L;

    private boolean is_fixed_rsrv;

    private String etc; // 기타 사항

    private Long amount; // 예금일때 한번에 넣을 금액
    private Long amount_regular; // 적금일때 한달에 넣을 금액

    @Column(nullable = false)
    private Date start_date;

    @Column(nullable = false)
    private Date end_date;

    @Column(nullable = false)
    private UUID member_id;
}
