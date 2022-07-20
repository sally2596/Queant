package com.ssafy.queant.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.ssafy.queant.model.oauth.*;
import com.ssafy.queant.model.service.OAuth.GoogleService;
import com.ssafy.queant.model.service.OAuth.KakaoService;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
@Log4j2
@RequestMapping("/social")
public class SocialController {

    private final Logger LOGGER = LoggerFactory.getLogger(SocialController.class);

    private final GoogleService googleService;
    private final KakaoService kakaoService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    SocialController(GoogleService googleService, KakaoService kakaoService) {
        this.googleService = googleService;
        this.kakaoService = kakaoService;
    }

    @GetMapping(value = "/kakao")
    public ResponseEntity<Object> getKakaoInitUrl() {
        return getInitUrl(kakaoService.kakaoInitUrl());
    }

    @GetMapping(value = "/google")
    public ResponseEntity<Object> getGoogleInitUrl() {
        return getInitUrl(googleService.googleInitUrl());
    }

    @GetMapping(value = "/kakao/login")
    public ResponseEntity<KakaoLoginDto> getKakaoUser(
            @RequestParam(value = "code") String authCode
    ) {
        // HTTP 통신을 위해 RestTemplate 활용
        KakaoOAuthRequest kakaoOAuthRequest= kakaoService.getKakaoOAuthRequest(authCode);
        try{
            // Http Header 설정
            //ResponseEntity<String> kakaoResponseJson = getResponseEntity(kakaoOAuthRequest);

        }catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body(null);
    }

    // authorization -> access token
    // access -> 사용자 정보
    @GetMapping(value = "/google/login")
    public ResponseEntity<GoogleLoginDto> getGoogleUser(
            @RequestParam(value = "code") String authCode
    ) {
        // HTTP 통신을 위해 RestTemplate 활용
        GoogleOAuthRequest googleOAuthRequest= googleService.getGoogleOAuthRequest(authCode);

        try {
            // Http Header 설정
            ResponseEntity<String> googleResponseJson = getResponseEntity(googleOAuthRequest);

            
            ObjectMapper objectMapper = getObjectMapper();

            // ObjectMapper를 통해 String to Object로 변환
            GoogleOAuthResponse googleLoginResponse = objectMapper.readValue(googleResponseJson.getBody(),new TypeReference<GoogleOAuthResponse>() {});

            LOGGER.info("[googleLoginResponse]"+googleLoginResponse);
            // 사용자의 정보는 JWT Token으로 저장되어 있고, Id_Token에 값을 저장한다.
            String jwtToken = googleLoginResponse.getIdToken();
            LOGGER.info("[jwtToken]"+jwtToken);
            // JWT Token을 전달해 JWT 저장된 사용자 정보 확인
            String requestUrl = UriComponentsBuilder.fromHttpUrl(googleService.getGoogleAccessTokenUrl() + "/tokeninfo").queryParam(
                    "id_token", jwtToken).toUriString();
            LOGGER.info("[requestUrl]"+requestUrl);
            String resultJson = restTemplate.getForObject(requestUrl, String.class);

            if(resultJson != null) {
                // member 저장
                // 있는 멤버라면 디비에 있는애 반환해주면 될 듯
                GoogleLoginDto userInfoDto = objectMapper.readValue(resultJson, new TypeReference<GoogleLoginDto>() {});
                LOGGER.info("[google-auth]");
                LOGGER.info(String.valueOf(userInfoDto));
                return ResponseEntity.ok().body(userInfoDto);
            }
            else {
                throw new Exception("Google OAuth failed!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body(null);
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
        return objectMapper;
    }

    private ResponseEntity<String> getResponseEntity(GoogleOAuthRequest googleOAuthRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GoogleOAuthRequest> googleRequestEntity = new HttpEntity<>(googleOAuthRequest, headers);
        ResponseEntity<String> googleResponseJson =
                restTemplate.postForEntity(googleService.getGoogleAccessTokenUrl() + "/token", googleRequestEntity, String.class);
        return googleResponseJson;
    }

    public ResponseEntity<Object> getInitUrl(String authUrl){
        try {
            URI redirectUri = new URI(authUrl);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirectUri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }
}
