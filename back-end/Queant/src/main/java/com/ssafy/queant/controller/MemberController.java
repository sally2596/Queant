package com.ssafy.queant.controller;

import com.ssafy.queant.model.service.EmailService;
import com.ssafy.queant.model.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;

    @PostMapping("/signup")
    public void signUp(){
        log.info("signup");
    }

    @PostMapping("/emailcheck")
    //200은 성공 409중복되었다.
    public ResponseEntity<?> emailCheck(@RequestBody Map<String,String> email) throws Exception {
        if(memberService.emailCheck(email.get("email"))) return new ResponseEntity<>(HttpStatus.CONFLICT);
        emailService.sendMessage(email.get("email"));
        log.info("[emailCheck] 이메일이 발송되었습니다. email : {}", email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/emailverify")
    @ResponseBody
    public ResponseEntity<?> verifyCode(@RequestBody Map<String,String>  code) throws Exception {

        log.info("[verifyCode] 입력받은 코드 : {}" , code.get("code"));
        if(emailService.verifyCode(code.get("code"))) return new ResponseEntity<>(HttpStatus.OK);
        else log.info("[verifyCode] 입력받은 코드가 일치하지 않습니다.");
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
