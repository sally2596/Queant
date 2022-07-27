package com.ssafy.queant.model.service.OAuth;

import com.ssafy.queant.model.dto.member.MemberDto;
import com.ssafy.queant.model.entity.member.Gender;
import com.ssafy.queant.model.entity.member.Social;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KakaoService {

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

    public MemberDto jsonToMemberDto(String resultData) {
        JSONObject user = new JSONObject(resultData) ;

        String name = user.get("nickname").toString();
        String email = user.get("email").toString();

        MemberDto member = MemberDto.builder()
                .name(name)
                .email(email)
                .social(Social.Kakao)
                .build();


        if(user.has("gender")) {
            Gender gender = user.get("gender").toString().equals("female") ? Gender.Female : Gender.Male;
            member.setGender(gender);
        }
        if(user.has("birthdate")) {
            String[] birth = user.get("birthdate").toString().split("-");
            int year = Integer.parseInt(birth[0]);
            int month = Integer.parseInt(birth[1]);
            int day = Integer.parseInt(birth[2]);
            Date birthdate = new Date(year - 1900, month - 1, day);
            member.setBirthdate(birthdate);
        }

        log.info("[KaKao OAuth member] "+member);
        return member;
    }
}
