package com.ssafy.queant.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomProductDto {
    private int product_id;
    private String institution_name;
    private String product_name;
    private boolean is_deposit;
    private float base_rate;
    private float special_rate =0L;
    private boolean is_fixed_rsrv;
    private String etc; // 기타 사항
    private Long amount; // 예금일때 한번에 넣을 금액
    private Long amount_regular; // 적금일때 한달에 넣을 금액
    private Date start_date;
    private Date end_date;
    private UUID member_id;
}