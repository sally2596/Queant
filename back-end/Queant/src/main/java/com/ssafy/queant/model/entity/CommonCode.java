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
public class CommonCode {
    @Id
    private char code_id;
    @Column(nullable = false)
    private String code_value;

}
