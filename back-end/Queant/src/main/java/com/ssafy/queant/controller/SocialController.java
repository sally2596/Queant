package com.ssafy.queant.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.queant.model.dto.LoginResultDto;
import com.ssafy.queant.model.dto.MemberDto;
import com.ssafy.queant.model.oauth.OAuthResponseDto;
import com.ssafy.queant.model.service.OAuth.GoogleService;
import com.ssafy.queant.model.service.OAuth.KakaoService;
import com.ssafy.queant.model.service.OAuth.NaverService;
import com.ssafy.queant.model.service.OAuth.OAuthService;
import com.ssafy.queant.security.JwtTokenProvider;
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

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin("*")
@Log4j2
@RequestMapping("/social")
public class SocialController {

    private final Logger LOGGER = LoggerFactory.getLogger(SocialController.class);

    private final GoogleService googleService;
    private final KakaoService kakaoService;
    private final NaverService naverService;
    private final OAuthService oAuthService;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    SocialController(GoogleService googleService, KakaoService kakaoService, NaverService naverService, OAuthService oAuthService, JwtTokenProvider jwtTokenProvider) {
        this.googleService = googleService;
        this.kakaoService = kakaoService;
        this.naverService = naverService;
        this.oAuthService = oAuthService;
        this.jwtTokenProvider = jwtTokenProvider;
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
    public ResponseEntity<Map<String, String>> getNaverUser(
            @RequestParam(value = "code") String authCode,
            @RequestParam(value = "state") String state
    ) throws JsonProcessingException {

        LinkedMultiValueMap<String, String> oAuthRequest = naverService.getNaverOAuthRequest(authCode, state);
        String accessTokenUrl = naverService.getNaverTokenUrl();
        String userUrl = naverService.getNaverUserUrl();

        String token = getToken(oAuthRequest,accessTokenUrl);

        String resultData = getUserDatabyAccessToken(userUrl, token);

        MemberDto member = naverService.jsonToMemberDto(resultData);

        if(!oAuthService.emailCheck(member.getEmail())){
            oAuthService.register(member);
        }

        LoginResultDto result = oAuthService.login(member);
        if(result.getResult().equals("SUCCESS")){
            Map<String, String> response = new HashMap<>();
            response.put("AccessToken", jwtTokenProvider.createToken(member.getEmail()));
            response.put("RefreshToken", result.getRefreshToken());
            return new ResponseEntity<Map<String,String>>(response, HttpStatus.OK);

        } else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/kakao/login")
    public ResponseEntity<Map<String, String>> getKakaoUser(
            @RequestParam(value = "code") String authCode
    ) throws JsonProcessingException {
        // HTTP 통신을 위해 RestTemplate 활용
        LinkedMultiValueMap<String, String> oAuthRequest = kakaoService.getKakaoOAuthRequest(authCode);
        String accessTokenUrl = kakaoService.getKakaoTokenUrl();
        String userUrl = kakaoService.getKakaoUserUrl();

        String token = getToken(oAuthRequest,accessTokenUrl);

        String resultData = getUserDatabyAccessToken(userUrl, token);

        MemberDto member = kakaoService.jsonToMemberDto(resultData);

        if(!oAuthService.emailCheck(member.getEmail())){
            oAuthService.register(member);
        }

        LoginResultDto result = oAuthService.login(member);
        if(result.getResult().equals("SUCCESS")){
            Map<String, String> response = new HashMap<>();
            response.put("AccessToken", jwtTokenProvider.createToken(member.getEmail()));
            response.put("RefreshToken", result.getRefreshToken());
            return new ResponseEntity<Map<String,String>>(response, HttpStatus.OK);

        } else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/google/login")
    public ResponseEntity<Map<String, String>> getGoogleUser(
            @RequestParam(value = "code") String authCode
    ) throws JsonProcessingException {
        // HTTP 통신을 위해 RestTemplate 활용
        LinkedMultiValueMap<String, String> oAuthRequest = googleService.getGoogleOAuthRequest(authCode);
        String accessTokenUrl = googleService.getGoogleTokenUrl();
        String userUrl = googleService.getGoogleUserUrl();

        String token = getToken(oAuthRequest,accessTokenUrl);

        String resultData = getUserDatabyAccessToken(userUrl, token);

        MemberDto member = googleService.jsonToMemberDto(resultData);

        if(!oAuthService.emailCheck(member.getEmail())){
            oAuthService.register(member);
        }

        LoginResultDto result = oAuthService.login(member);

        if(result.getResult().equals("SUCCESS")){
            Map<String, String> response = new HashMap<>();
            response.put("AccessToken", jwtTokenProvider.createToken(member.getEmail()));
            response.put("RefreshToken", result.getRefreshToken());
            return new ResponseEntity<Map<String,String>>(response, HttpStatus.OK);

        } else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
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
