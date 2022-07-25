package com.ssafy.queant.model.dto.member;

import lombok.Data;

@Data
public class OAuthResponseDto {

    private String accessToken;
    private String expiresIn;
    private String refreshToken;
    private String refreshTokenExpiresIn;
    private String scope;
    private String tokenType;
    private String idToken;

}

