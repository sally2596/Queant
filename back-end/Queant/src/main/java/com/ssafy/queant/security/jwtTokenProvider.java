package com.ssafy.queant.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class jwtTokenProvider {

    @Value("spring.jwt.secret")
    private String secretKey;
    private Key key;
    private long tokenValidMilisecond = 1000L * 60 * 60; //1시간만 토큰 유효

    @PostConstruct
    protected void init(){key  = Keys.hmacShaKeyFor(secretKey.getBytes());}

    public String createToken(String userPk){
        Claims claims = Jwts.claims().setSubject(userPk);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+tokenValidMilisecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(){
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
