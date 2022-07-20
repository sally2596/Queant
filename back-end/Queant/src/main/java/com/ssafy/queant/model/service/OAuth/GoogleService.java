package com.ssafy.queant.model.service.OAuth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoogleService {

    @Value("${google.access-token.url}")
    private String googleAccessTokenUrl;

    @Value("${google.auth-token.url}")
    private String googleAuthTokenUrl;

    @Value("${google.redirect.uri}")
    private String googleRedirectUrl;

    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.client.secret}")
    private String googleClientSecret;

    @Value("${google.auth.scope}")
    private String scopes;

    public String getGoogleAccessTokenUrl() {
        return googleAccessTokenUrl;
    }

    public String getGoogleAuthTokenUrl() {
        return googleAuthTokenUrl;
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

        return getGoogleAuthTokenUrl()
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

//    public ResponseEntity<String> response(String authCode){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<GoogleOAuthRequest> googleRequestEntity = new HttpEntity<>(getGoogleOAuthRequest(authCode), headers);
//        ResponseEntity<String> googleResponseJson =
//                restTemplate.postForEntity(getGoogleAccessTokenUrl() + "/token", googleRequestEntity, String.class);
//
//        // ObjectMapper를 통해 String to Object로 변환
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
//        GoogleOAuthResponse googleLoginResponse = objectMapper.readValue(googleResponseJson.getBody(),
//                new TypeReference<GoogleOAuthResponse>() {});
//        return googleResponseJson;
//    }
}
