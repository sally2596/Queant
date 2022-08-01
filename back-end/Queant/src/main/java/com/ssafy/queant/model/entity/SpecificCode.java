package com.ssafy.queant.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
//@Entity
//@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpecificCode implements Serializable {
    @Id
    private char code_id;
    @Id
    private int scode_id;
    @Column(nullable = false)
    private String scode_value;
}
