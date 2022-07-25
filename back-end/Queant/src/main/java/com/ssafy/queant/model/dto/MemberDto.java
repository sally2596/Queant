package com.ssafy.queant.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.queant.model.entity.Gender;
import com.ssafy.queant.model.entity.MemberRole;
import com.ssafy.queant.model.entity.Social;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema
public class MemberDto implements UserDetails {
    @ApiModelProperty(hidden = true)
    private UUID memberId;
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "비밀번호")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Schema(description = "이름 혹은 닉네임")
    private String name;
    @Schema(description = "성별(Female/Male)")
    private Gender gender;
    @Schema(description = "생일")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;
    @ApiModelProperty(hidden = true)
    private int portfolioCnt;
    @ApiModelProperty(hidden = true)
    private Social social;
    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String refreshToken;
    @ApiModelProperty(hidden = true)
    private boolean enabled;

    @ApiModelProperty(hidden = true)
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
        return this.password;
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
        return enabled;
    }
}
