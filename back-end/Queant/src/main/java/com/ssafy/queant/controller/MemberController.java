package com.ssafy.queant.controller;

import com.ssafy.queant.model.dto.LoginResultDto;
import com.ssafy.queant.model.dto.MemberDto;
import com.ssafy.queant.model.service.EmailService;
import com.ssafy.queant.model.service.MemberService;
import com.ssafy.queant.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/emailcheck")
    //200은 성공 409중복되었다.
    public ResponseEntity<?> emailCheck(@RequestBody Map<String,String> email) throws Exception {
        if(memberService.emailCheck(email.get("email"))) return new ResponseEntity<>(HttpStatus.CONFLICT);
        emailService.sendMessage(email.get("email"));
        log.info("[emailCheck] 이메일이 발송되었습니다. email : {}", email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/emailverify")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String,String>  code) throws Exception {

        log.info("[verifyCode] 입력받은 코드 : {}" , code.get("code"));
        if(emailService.verifyCode(code.get("code"))) return new ResponseEntity<>(HttpStatus.OK);
        else log.info("[verifyCode] 입력받은 코드가 일치하지 않습니다.");
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody MemberDto memberDto){
        log.info("[Register] 정보 확인 비번 : {}" , memberDto.getPassword());
        if(memberService.register(memberDto)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/login")
    public ResponseEntity<?> LogIn(@RequestBody Map<String,String> loginInfo){

        LoginResultDto result = memberService.login(loginInfo.get("email"), loginInfo.get("password"));
        if(result.getResult().equals("SUCCESS")){
            Map<String, String> response = new HashMap<>();
            response.put("AccessToken", jwtTokenProvider.createToken(loginInfo.get("email")));
            response.put("RefreshToken", result.getRefreshToken());
            return new ResponseEntity<Map<String,String>>(response, HttpStatus.OK);

        } else if (result.getResult().equals("EmailError")){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }
}
