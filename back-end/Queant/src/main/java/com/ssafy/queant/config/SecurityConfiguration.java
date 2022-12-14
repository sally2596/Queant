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
            "/member/register", "/member/login", "/member/refreshtoken", "/", "/contents/**",
            "/member/list","/member/roles","/member/status","/member/password","/portfolio/**", "/product/**",
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
                .httpBasic().disable() // rest api ????????? ???????????? ????????????. ??????????????? ???????????? ???????????? ???????????? ??????????????? ??????.
                .cors().configurationSource(corsConfigurationSource())

                .and()
                .csrf().disable() // rest api????????? csrf ????????? ?????????????????? disable??????.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token?????? ??????????????? ????????? ?????????????????? ????????????.

                .and()
                .authorizeRequests() // ?????? ??????????????? ?????? ???????????? ??????
                .antMatchers(SWAGGER_WHITELIST).permitAll()// swagger permit
                .antMatchers(AUTH_WHITELIST).permitAll() // login and register
                .antMatchers("/bank/**").permitAll() // ?????? ??????
                .antMatchers("/search/**").permitAll() // ?????? ??????
                .antMatchers("/member/roles", "/member/list").hasAnyRole("ADMIN")
                .antMatchers("/product/**").permitAll() // ?????? ??????
                // ????????????
                .anyRequest().hasAnyRole("USER", "ADMIN", "SUPER")// ?????? ????????? ????????? ?????? ????????? ????????? ?????? ??????

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
