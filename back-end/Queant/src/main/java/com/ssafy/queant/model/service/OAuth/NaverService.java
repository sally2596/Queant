package com.ssafy.queant.model.service.OAuth;

import com.ssafy.queant.model.dto.MemberDto;
import com.ssafy.queant.model.entity.Gender;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Date;
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

    public String uniToKor(String uni){
        StringBuffer result = new StringBuffer();

        for(int i=0; i<uni.length(); i++){
            if(uni.charAt(i) == '\\' &&  uni.charAt(i+1) == 'u'){
                Character c = (char)Integer.parseInt(uni.substring(i+2, i+6), 16);
                result.append(c);
                i+=5;
            }else{
                result.append(uni.charAt(i));
            }
        }
        return result.toString();
    }

    public MemberDto jsonToMemberDto(String resultData) {
        JSONObject jObject = new JSONObject(resultData) ;
        JSONObject user = new JSONObject(jObject.get("response").toString());

        int year = Integer.parseInt(user.get("birthyear").toString());
        int month = Integer.parseInt(user.get("birthday").toString().split("-")[0]);
        int day = Integer.parseInt(user.get("birthday").toString().split("-")[1]);

        String name = uniToKor(user.get("name").toString());
        String email = user.get("email").toString();
        Gender gender = user.get("gender").toString().equals("F")? Gender.Female:Gender.Male;
        Date birthdate =new Date(year-1900,month-1,day);


        MemberDto member = MemberDto.builder()
                .name(name)
                .email(email)
                .gender(gender)
                .birthdate( birthdate)
                .build();
        LOGGER.info("[Naver OAuth member] "+member);
        return member;
    }
}
