package com.ssafy.queant.model.service.OAuth;

import com.ssafy.queant.model.oauth.GoogleOAuthRequest;
import com.ssafy.queant.model.oauth.KakaoOAuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KakaoService {
    @Value("${kakao.access-token.url}")
    private String kakaoAccessTokenUrl;

    @Value("${kakao.auth-token.url}")
    private String kakaoAuthTokenUrl;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUrl;

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    public String getKakaoAccessTokenUrl() {
        return kakaoAccessTokenUrl;
    }

    public String getKakaoAuthTokenUrl() {
        return kakaoAuthTokenUrl;
    }

    public String getKakaoRedirectUrl() {
        return kakaoRedirectUrl;
    }

    public String getKakaoClientId() {
        return kakaoClientId;
    }

    // Kakao 로그인 URL 생성 로직
    public String kakaoInitUrl() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", getKakaoClientId());
        params.put("redirect_uri", getKakaoRedirectUrl());
        params.put("response_type", "code");

        String paramStr = params.entrySet().stream()
                .map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));

        return getKakaoAuthTokenUrl()
                + "?"
                + paramStr;
    }

    public KakaoOAuthRequest getKakaoOAuthRequest(String authCode){
        return KakaoOAuthRequest.builder()
                .clientId(getKakaoClientId())
                .code(authCode)
                .redirectUri(getKakaoRedirectUrl())
                .grantType("authorization_code")
                .build();
    }
}
