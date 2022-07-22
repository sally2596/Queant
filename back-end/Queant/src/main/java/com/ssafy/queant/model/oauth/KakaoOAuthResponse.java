package com.ssafy.queant.model.oauth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoOAuthResponse {
    private String tokenType;
    private String accessToken;
    private String idToken;
    private long expiresIn;
    private String refreshToken;
    private String refreshTokenExpiresIn;
    private String scope;
}
