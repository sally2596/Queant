package com.ssafy.queant.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table
public class Member {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID member_id;
    @Column(nullable = true, unique=true)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
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

    private String refreshToken;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addMemberRole(MemberRole memberRole){
        roleSet.add(memberRole);
    }

}
