package com.ssafy.queant.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.queant.model.dto.member.LoginResultDto;
import com.ssafy.queant.model.dto.member.MemberDto;
import com.ssafy.queant.model.dto.member.OAuthResponseDto;
import com.ssafy.queant.model.service.OAuth.GoogleService;
import com.ssafy.queant.model.service.OAuth.KakaoService;
import com.ssafy.queant.model.service.OAuth.NaverService;
import com.ssafy.queant.model.service.OAuth.OAuthService;
import com.ssafy.queant.security.JwtTokenProvider;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@CrossOrigin("*")
@Slf4j
@RequestMapping("/social")
public class SocialController {

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

    @ApiResponses({
            @ApiResponse(code = 200, message="네이버 로그인창으로 redirect하는 url을 가져오는데 성공했습니다."),
    })
    @Operation(summary = "네이버 로그인 창으로 이동", description = "네이버 로그인창 url을 가져옴")
    @GetMapping(value = "/naver")
    public ResponseEntity<Object> getNaverInitUrl() {
        return ResponseEntity.ok().body(naverService.naverInitUrl());
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="카카오 로그인창으로 redirect하는 url을 가져오는데 성공했습니다."),
    })
    @Operation(summary = "카카오 로그인 창으로 이동", description = "카카오 로그인창 url을 가져옴")
    @GetMapping(value = "/kakao")
    public ResponseEntity<Object> getKakaoInitUrl() {
        return ResponseEntity.ok().body(kakaoService.kakaoInitUrl());
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="구글 로그인창으로 redirect하는 url을 가져오는데 성공했습니다."),
    })
    @Operation(summary = "구글 로그인 창으로 이동", description = "구글 로그인창 url을 가져옴")
    @GetMapping(value = "/google")
    public ResponseEntity<Object> getGoogleInitUrl() {
        return ResponseEntity.ok().body(googleService.googleInitUrl());
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="네이버 사용자 저장 및 로그인에 성공했습니다."),
            @ApiResponse(code = 409, message="기존 회원입니다."),
    })
    @Operation(summary = "네이버 사용자 저장 및 로그인", description = "네이버 사용자가 DB에 없다면 저장하고 있다면 로그인 처리")
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

        return oAuthService.makeResponseEntity(member, result);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="카카오 사용자 저장 및 로그인에 성공했습니다."),
            @ApiResponse(code = 409, message="기존 회원입니다."),
    })
    @Operation(summary = "카카오 사용자 저장 및 로그인", description = "카카오 사용자가 DB에 없다면 저장하고 있다면 로그인 처리 ")
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

        return oAuthService.makeResponseEntity(member, result);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="구글 사용자 저장 및 로그인에 성공했습니다."),
            @ApiResponse(code = 409, message="기존 회원입니다."),
    })
    @Operation(summary = "구글 사용자 저장 및 로그인", description = "구글 사용자가 DB에 없다면 저장하고 있다면 로그인 처리 ")
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

        return oAuthService.makeResponseEntity(member, result);
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
