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
public class GoogleService {

    private final Logger LOGGER = LoggerFactory.getLogger(GoogleService.class);

    // 구글은 access token 사용
    @Value("${google.token.url}")
    private String googleTokenUrl;

    @Value("${google.user.url}")
    private String googleUserUrl;

    @Value("${google.auth.url}")
    private String googleAuthUrl;

    @Value("${google.redirect.uri}")
    private String googleRedirectUrl;

    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.client.secret}")
    private String googleClientSecret;

    @Value("${google.auth.scope}")
    private String scopes;

    public String getGoogleTokenUrl() {
        return googleTokenUrl;
    }

    public String getGoogleUserUrl() {
        return googleUserUrl;
    }

    public String getGoogleAuthUrl() {
        return googleAuthUrl;
    }

    public String getGoogleClientId() {
        return googleClientId;
    }

    public String getGoogleRedirectUri() {
        return googleRedirectUrl;
    }

    public String getGoogleClientSecret() {
        return googleClientSecret;
    }

    // scope의 값을 보내기 위해 띄어쓰기 값을 UTF-8로 변환하는 로직 포함
    public String getScopeUrl() {
//        return scopes.stream().collect(Collectors.joining(","))
//                .replaceAll(",", "%20");
        return scopes.replaceAll(",", "%20");
    }

    // Google 로그인 URL 생성 로직
    public String googleInitUrl() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", getGoogleClientId());
        params.put("redirect_uri", getGoogleRedirectUri());
        params.put("response_type", "code");
        params.put("scope", getScopeUrl());

        String paramStr = params.entrySet().stream()
                .map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));

        return getGoogleAuthUrl()
                + "/o/oauth2/v2/auth"
                + "?"
                + paramStr;
    }

    public LinkedMultiValueMap<String, String> getGoogleOAuthRequest(String authCode){
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", getGoogleClientId());
        params.add("redirect_uri", getGoogleRedirectUri());
        params.add("code", authCode);
        params.add("client_secret", getGoogleClientSecret());
        return params;
    }

    


}
