package com.ssafy.queant.model.entity.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bank {
    @Id
    private int bankId;

    @Column(nullable = false)
    private String scodeId;

    @Column(nullable = false)
    private String bankName;

    private String homepage;
    private String tel;
    private String picture;
}
