package com.ssafy.queant.model.service.OAuth;

import com.ssafy.queant.controller.SocialController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NaverService {

    private final Logger LOGGER = LoggerFactory.getLogger(NaverService.class);

    // Naver는 access token 사용
    // token 요청할 url
    @Value("${naver.token.url}")
    private String naverTokenUrl;

    // 유저 정보 요청 url
    @Value("${naver.user.url}")
    private String naverUserUrl;

    // login 창 띄울 url
    @Value("${naver.auth.url}")
    private String naverAuthUrl;

    @Value("${naver.redirect.uri}")
    private String naverRedirectUrl;

    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;

    public String getNaverTokenUrl() {
        return naverTokenUrl;
    }

    public String getNaverUserUrl() {
        return naverUserUrl;
    }

    public String getNaverAuthUrl() {
        return naverAuthUrl;
    }

    public String getNaverRedirectUrl() {
        return naverRedirectUrl;
    }

    public String getNaverClientId() {
        return naverClientId;
    }

    public String getNaverClientSecret() {
        return naverClientSecret;
    }

    // Naver 로그인 URL 생성 로직
    public String naverInitUrl() {
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130,random).toString(32);
        LOGGER.info("state:"+state);
//        String state = null;
//        try {
//            state = URLEncoder.encode(new BigInteger(130,random).toString(32), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }

        Map<String, Object> params = new HashMap<>();
        params.put("client_id", getNaverClientId());
        params.put("redirect_uri", getNaverRedirectUrl());
        params.put("response_type", "code");
        params.put("state", state);

        String paramStr = params.entrySet().stream()
                .map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));

        return getNaverAuthUrl()
                + "?"
                + paramStr;
    }

    public LinkedMultiValueMap<String, String> getNaverOAuthRequest(String authCode,String state){
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", getNaverClientId());
        params.add("client_secret", getNaverClientSecret());
        params.add("redirect_uri", getNaverRedirectUrl());
        params.add("state", state);
        params.add("code", authCode);
        return params;
    }
}
