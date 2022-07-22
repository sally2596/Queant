package com.ssafy.queant.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.queant.model.oauth.*;
import com.ssafy.queant.model.service.OAuth.GoogleService;
import com.ssafy.queant.model.service.OAuth.KakaoService;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
@Log4j2
@CrossOrigin("*")
@RequestMapping("/social")
public class SocialController {

    private final Logger LOGGER = LoggerFactory.getLogger(SocialController.class);

    private final GoogleService googleService;
    private final KakaoService kakaoService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

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
    public ResponseEntity<String> getKakaoUser(
            @RequestParam(value = "code") String authCode
    ) {
        // HTTP 통신을 위해 RestTemplate 활용
        LinkedMultiValueMap<String, String> oAuthRequest= kakaoService.getKakaoOAuthRequest(authCode);
        String accessTokenUrl = kakaoService.getKakaoTokenUrl();
        String userUrl = kakaoService.getKakaoUserUrl();

        try {
            /***
             * Authorization Code를 넣어 토큰을 가져오는 작업
             *
             * OAuthRequest: 소셜의 OAuth Request 파라미터들을 담음
             * accessTokenUrl: 각 소셜의 accessToken을 받아올 Url (Authorization Code가 이미 들어가 있음)
             */
            ResponseEntity<String> responseJson = getResponseEntity(oAuthRequest,accessTokenUrl);

            /***
             * 응답받은 Json ->token 추출
             */
            OAuthResponseDto responseDto = objectMapper.readValue(responseJson.getBody(),new TypeReference<OAuthResponseDto>() {});
            String token = responseDto.getAccessToken();


            /***
             * token 넣어서 data 가져올 url 생성 -> 사용자 data 받아오기
             * google, kakao: openId를 통한 데이터 받아오기 가능
             */
           String resultData = getUserDatabyAccessToken(userUrl,token);
           LOGGER.info("resultData:\n"+resultData);
           return ResponseEntity.ok().body(resultData);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping(value = "/google/login")
    public ResponseEntity<String> getGoogleUser(
            @RequestParam(value = "code") String authCode
    ) {
        // HTTP 통신을 위해 RestTemplate 활용
        LinkedMultiValueMap<String, String> oAuthRequest= googleService.getGoogleOAuthRequest(authCode);
        String accessTokenUrl = googleService.getGoogleTokenUrl();
        String userUrl = googleService.getGoogleUserUrl();

        try {
            /***
             * Authorization Code를 넣어 토큰을 가져오는 작업
             *
             * OAuthRequest: 소셜의 OAuth Request 파라미터들을 담음
             * accessTokenUrl: 각 소셜의 accessToken을 받아올 Url (Authorization Code가 이미 들어가 있음)
             */
            ResponseEntity<String> responseJson = getResponseEntity(oAuthRequest,accessTokenUrl);

            /***
             * 응답받은 Json ->token 추출
             */
            OAuthResponseDto responseDto = objectMapper.readValue(responseJson.getBody(),new TypeReference<OAuthResponseDto>() {});
            String token = responseDto.getAccessToken();

            /***
             * token 넣어서 data 가져올 url 생성 -> 사용자 data 받아오기
             * google, kakao: openId를 통한 데이터 받아오기 가능
             */
            String userData = getUserDatabyAccessToken(userUrl,token);
            LOGGER.info("userData:\n"+userData);
            return ResponseEntity.ok().body(userData);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(null);
    }

    private String getUserDatabyAccessToken(String url, String accessToken) {
        // header 설정: AccessToken을 담음
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        return restTemplate.exchange(url,HttpMethod.GET,request,String.class).getBody();
    }

    private ResponseEntity<String> getResponseEntity(LinkedMultiValueMap<String, String> params,String accessTokenUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(accessTokenUrl, request, String.class);
        return response;
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

//    private ResponseEntity<String> OLDVERSION_getResponseEntity(GoogleOAuthRequest googleOAuthRequest) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<GoogleOAuthRequest> googleRequestEntity = new HttpEntity<>(googleOAuthRequest, headers);
//        ResponseEntity<String> googleResponseJson =
//                restTemplate.postForEntity(googleService.getGoogleTokenUrl() + "/token", googleRequestEntity, String.class);
//        return googleResponseJson;
//    }
//
//
//    @GetMapping(value = "/OLDVERSION/google/login")
//    public ResponseEntity<GoogleLoginDto> OLDVERSIONgetGoogleUser(
//            @RequestParam(value = "code") String authCode
//    ) {
//        // HTTP 통신을 위해 RestTemplate 활용
//        LinkedMultiValueMap<String, String> googleOAuthRequest= googleService.getGoogleOAuthRequest(authCode);
//        String accessTokenUrl = googleService.getGoogleTokenUrl() + "/token";
//
//        try {
//            /***
//             * Authorization Code를 넣어 토큰을 가져오는 작업
//             *
//             * OAuthRequest: 소셜의 OAuth Request 파라미터들을 담음
//             * accessTokenUrl: 각 소셜의 accessToken을 받아올 Url (Authorization Code가 이미 들어가 있음)
//             */
//            ResponseEntity<String> googleResponseJson = getResponseEntity(googleOAuthRequest,accessTokenUrl);
//
//
//            /***
//             * 응답받은 Json -> AccessToken 추출
//             */
//            OAuthResponseDto googleLoginResponse = objectMapper.readValue(googleResponseJson.getBody(),new TypeReference<OAuthResponseDto>() {});
//            String jwtToken = googleLoginResponse.getIdToken();
//
//
//            // JWT Token을 전달해 JWT 저장된 사용자 정보 확인
//            String requestUrl = UriComponentsBuilder.fromHttpUrl(googleService.getGoogleTokenUrl() + "/tokeninfo").queryParam(
//                    "id_token", jwtToken).toUriString();
//            LOGGER.info("[requestUrl]"+requestUrl);
//            String resultJson = restTemplate.getForObject(requestUrl, String.class);
//
//            if(resultJson != null) {
//                // member 저장
//                // 있는 멤버라면 디비에 있는애 반환해주면 될 듯
//                GoogleLoginDto userInfoDto = objectMapper.readValue(resultJson, new TypeReference<GoogleLoginDto>() {});
//                LOGGER.info("[google-auth]");
//                LOGGER.info(String.valueOf(userInfoDto));
//                return ResponseEntity.ok().body(userInfoDto);
//            }
//            else {
//                throw new Exception("Google OAuth failed!");
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.badRequest().body(null);
//    }

}
