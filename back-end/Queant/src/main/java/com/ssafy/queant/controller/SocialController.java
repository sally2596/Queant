package com.ssafy.queant.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.queant.model.dto.MemberDto;
import com.ssafy.queant.model.oauth.OAuthResponseDto;
import com.ssafy.queant.model.service.OAuth.GoogleService;
import com.ssafy.queant.model.service.OAuth.KakaoService;
import com.ssafy.queant.model.service.OAuth.NaverService;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@CrossOrigin("*")
@Log4j2
@RequestMapping("/social")
public class SocialController {

    private final Logger LOGGER = LoggerFactory.getLogger(SocialController.class);

    private final GoogleService googleService;
    private final KakaoService kakaoService;
    private final NaverService naverService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    SocialController(GoogleService googleService, KakaoService kakaoService, NaverService naverService) {
        this.googleService = googleService;
        this.kakaoService = kakaoService;
        this.naverService = naverService;
    }

    @GetMapping(value = "/naver")
    public ResponseEntity<Object> getNaverInitUrl() {
        return ResponseEntity.ok().body(naverService.naverInitUrl());
    }

    @GetMapping(value = "/kakao")
    public ResponseEntity<Object> getKakaoInitUrl() {
        return ResponseEntity.ok().body(kakaoService.kakaoInitUrl());
    }

    @GetMapping(value = "/google")
    public ResponseEntity<Object> getGoogleInitUrl() {
        return ResponseEntity.ok().body(googleService.googleInitUrl());
    }

    @GetMapping(value = "/naver/login")
    public ResponseEntity<String> getNaverUser(
            @RequestParam(value = "code") String authCode,
            @RequestParam(value = "state") String state
    ) throws JsonProcessingException {

        LinkedMultiValueMap<String, String> oAuthRequest = naverService.getNaverOAuthRequest(authCode, state);
        String accessTokenUrl = naverService.getNaverTokenUrl();
        String userUrl = naverService.getNaverUserUrl();

        String token = getToken(oAuthRequest,accessTokenUrl);

        String resultData = getUserDatabyAccessToken(userUrl, token);

        MemberDto member = naverService.jsonToMemberDto(resultData);

        // 멤버가 없으면 저장 + 로그인

        // 멤버가 있으면 로그인 처리

        return ResponseEntity.ok().body(resultData);

    }

    @GetMapping(value = "/kakao/login")
    public ResponseEntity<String> getKakaoUser(
            @RequestParam(value = "code") String authCode
    ) throws JsonProcessingException {
        // HTTP 통신을 위해 RestTemplate 활용
        LinkedMultiValueMap<String, String> oAuthRequest = kakaoService.getKakaoOAuthRequest(authCode);
        String accessTokenUrl = kakaoService.getKakaoTokenUrl();
        String userUrl = kakaoService.getKakaoUserUrl();

        String token = getToken(oAuthRequest,accessTokenUrl);

        String resultData = getUserDatabyAccessToken(userUrl, token);

        MemberDto member = kakaoService.jsonToMemberDto(resultData);

        return ResponseEntity.ok().body(resultData);
//        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping(value = "/google/login")
    public ResponseEntity<String> getGoogleUser(
            @RequestParam(value = "code") String authCode
    ) throws JsonProcessingException {
        // HTTP 통신을 위해 RestTemplate 활용
        LinkedMultiValueMap<String, String> oAuthRequest = googleService.getGoogleOAuthRequest(authCode);
        String accessTokenUrl = googleService.getGoogleTokenUrl();
        String userUrl = googleService.getGoogleUserUrl();

        String token = getToken(oAuthRequest,accessTokenUrl);

        String resultData = getUserDatabyAccessToken(userUrl, token);


        return ResponseEntity.ok().body(resultData);
//        return ResponseEntity.badRequest().body(null);
    }

    private String getUserDatabyAccessToken(String url, String accessToken) {
        // header 설정: AccessToken을 담음
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        return restTemplate.exchange(url, HttpMethod.GET, request, String.class).getBody();
    }

    private String getToken(LinkedMultiValueMap<String, String> params, String accessTokenUrl) throws JsonProcessingException {
        // 토큰 요청
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> responseJson= restTemplate.postForEntity(accessTokenUrl, request, String.class);

        OAuthResponseDto responseDto = objectMapper.readValue(responseJson.getBody(), new TypeReference<OAuthResponseDto>() {});
        String token = responseDto.getAccessToken();

        return token;
    }
}
