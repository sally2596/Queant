package com.ssafy.queant.config;

import com.ssafy.queant.security.JwtAuthenticationFilter;
import com.ssafy.queant.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/**/v3/api-docs",
            "/swagger-ui/index",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "**/swagger-ui.html",
            "/**/swagger-ui.html**",
            "/swagger-ui.html**",
            "/swagger-ui/**",
            "/img/**",
            "/webjars/**"
    };
    private static final String[] AUTH_WHITELIST = {
            "/social/**", "/member/emailcheck", "/member/emailverify",
            "/member/register", "/member/login", "/member/refreshtoken", "/contents/**"
    };
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .cors().configurationSource(corsConfigurationSource())

                .and()
                .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증하므로 세션은 필요없으므로 생성안함.

                .and()
                .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
                .antMatchers(SWAGGER_WHITELIST).permitAll()// swagger permit
                .antMatchers(AUTH_WHITELIST).permitAll() // login and register
                .antMatchers("/bank/**").permitAll() // 은행 정보
                .antMatchers("/search/**").permitAll() // 검색 정보
                .antMatchers("/member/roles", "/member/list").hasAnyRole("ADMIN")
                .antMatchers("/product/**").permitAll() // 상품 정보
                // 접근가능
                .anyRequest().hasAnyRole("USER", "ADMIN", "SUPER")// 그외 나머지 요청은 모두 인증된 회원만 접근 가능

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


//    @Override
//    public void configure(WebSecurity webSecurity){
//        webSecurity.ignoring().antMatchers(
//                "/v2/api-docs",
//                "/v3/api-docs",
//                "/**/v3/api-docs",
//                "/swagger-ui/index.html",
//                "/swagger-resources",
//                "/swagger-resources/**",
//                "/configuration/ui",
//                "/configuration/security",
//                "/swagger-ui.html",
//                "**/swagger-ui.html",
//                "/**/swagger-ui.html**",
//                "/swagger-ui.html**",
//                "/webjars/**"
//        );
//    }


}
