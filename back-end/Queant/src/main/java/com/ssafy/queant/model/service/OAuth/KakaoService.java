package com.ssafy.queant.model.service.OAuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KakaoService {

    private final Logger LOGGER = LoggerFactory.getLogger(KakaoService.class);

    // kakao는 access token 사용
    // token 요청할 url
    @Value("${kakao.token.url}")
    private String kakaoTokenUrl;

    // 유저 정보 요청 url
    @Value("${kakao.user.url}")
    private String kakaoUserUrl;

    // login 창 띄울 url
    @Value("${kakao.auth.url}")
    private String kakaoAuthUrl;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUrl;

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    public String getKakaoTokenUrl() {
        return kakaoTokenUrl;
    }

    public String getKakaoUserUrl() {
        return kakaoUserUrl;
    }

    public String getKakaoAuthUrl() {
        return kakaoAuthUrl;
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

        return getKakaoAuthUrl()
                + "?"
                + paramStr;
    }

    public LinkedMultiValueMap<String, String> getKakaoOAuthRequest(String authCode){
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", getKakaoClientId());
        params.add("redirect_uri", getKakaoRedirectUrl());
        params.add("code", authCode);
        return params;
    }

    
}
