package com.ssafy.queant.model.entity.portfolio;

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
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int portfolio_id;
    @Column(nullable = false)
    private UUID member_id;
    @Column(nullable = false)
    private String product_id;
    @Column(nullable = false)
    private int portfolio_no;
    @Column(nullable = false)
    private Long amount;
    @Column(nullable = false)
    private Date start_date;
    private Date end_date;
    @Column(nullable = false)
    @Builder.Default
    private Long special_rate = 0L;
    private Long amount_fixed;
}
