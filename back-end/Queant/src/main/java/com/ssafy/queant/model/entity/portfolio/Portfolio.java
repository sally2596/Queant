package com.ssafy.queant.model.entity.portfolio;

import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.member.MemberRole;
import com.ssafy.queant.model.entity.product.Conditions;
import com.ssafy.queant.model.entity.product.Options;
import com.ssafy.queant.model.entity.product.Product;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

import java.util.*;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Portfolio implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int portfolioId;

    @ManyToOne
    @JoinColumn(name="memberId")
    @ToString.Exclude
    private Member member;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "optionId")
    private Options option;

    @ManyToMany
    @Builder.Default
    private Set<Conditions> conditions = new HashSet<>();

    @Column(nullable = false)
    private int portfolioNo;
    @Column(nullable = false)
    private Long amount;
    private int saveTerm;
    @Column(nullable = false)
    private Date startDate;
    private Date endDate;

    public void addCondition(Conditions condition){
        conditions.add(condition);
    }
}
