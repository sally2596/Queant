package com.ssafy.queant.model.entity;

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
public class SpecificCode{
    @Id
    private String scodeId;

    @Column(nullable = false)
    private String codeId;

    @Column(nullable = false)
    private String scodeValue;
}
