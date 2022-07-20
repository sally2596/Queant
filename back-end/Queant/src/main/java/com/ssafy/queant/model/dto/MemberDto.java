package com.ssafy.queant.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.queant.model.entity.Gender;
import com.ssafy.queant.model.entity.MemberRole;
import com.ssafy.queant.model.entity.Social;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class MemberDto implements UserDetails {
    private UUID member_id;
    private String email;
    private String password;
    private String name;
    private Gender gender;
    private Date birthdate;
    private int portfolio_cnt;
    private Social social;
    private String refreshToken;

    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addMemberRole(MemberRole memberRole){
        roleSet.add(memberRole);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roleSet
                .stream()
                .map(MemberRole::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
