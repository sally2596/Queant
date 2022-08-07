package com.ssafy.queant.model.entity.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.queant.model.entity.portfolio.Portfolio;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.sound.sampled.Port;
import java.io.Serializable;
import java.sql.Date;
import java.util.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "roleSet")
@Table
public class Member implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID memberId;
    @Column(nullable = true, unique=true)
    @NaturalId
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = true)
    private String password;
    @Column(nullable = false, unique = false)
    private String name;
    @Column(nullable = true)
    private Gender gender;
    @Column(nullable = true)
    private Date birthdate;
    @Column(nullable = false)
    @Builder.Default
    private int portfolio_cnt = -1;
    @Column(nullable = false)
    @Builder.Default
    private Social social = Social.valueOf("None");
    @Column(nullable = false)
    @Builder.Default
    private boolean enabled = true;

    private String refreshToken;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

//    @OneToMany(mappedBy = "member")
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private List<Portfolio> portfolios = new ArrayList<>();

    public void addMemberRole(MemberRole memberRole){
        roleSet.add(memberRole);
    }

}
