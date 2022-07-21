package com.ssafy.queant.controller;

import com.ssafy.queant.model.dto.LoginResultDto;
import com.ssafy.queant.model.dto.MemberDto;
import com.ssafy.queant.model.dto.MemberRequestDto;
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
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/emailcheck")
    //200은 성공 409중복되었다.
    public ResponseEntity<?> emailCheck(@RequestBody MemberRequestDto requestDto) throws Exception {
        if(memberService.emailCheck(requestDto.getEmail())) return new ResponseEntity<>(HttpStatus.CONFLICT);
        emailService.sendMessage(requestDto.getEmail());
        log.info("[emailCheck] 이메일이 발송되었습니다. email : {}", requestDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/emailverify")
    public ResponseEntity<?> verifyCode(@RequestBody MemberRequestDto requestDto) throws Exception {

        log.info("[verifyCode] 입력받은 코드 : {}" , requestDto.getCode());
        if(emailService.verifyCode(requestDto.getCode())) return new ResponseEntity<>(HttpStatus.OK);
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
    public ResponseEntity<?> LogIn(@RequestBody MemberRequestDto requestDto){

        LoginResultDto result = memberService.login(requestDto.getEmail(), requestDto.getPassword());
        if(result.getResult().equals("SUCCESS")){
            Map<String, String> response = new HashMap<>();
            response.put("AccessToken", jwtTokenProvider.createToken(requestDto.getEmail()));
            response.put("RefreshToken", result.getRefreshToken());
            return new ResponseEntity<Map<String,String>>(response, HttpStatus.OK);

        } else if (result.getResult().equals("EmailError")){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    @PostMapping("/info")
    public ResponseEntity<?> Info(@RequestBody MemberRequestDto requestDto){
        MemberDto memberDto = memberService.findMember(requestDto.getEmail());
        if(memberDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
    }

    @PutMapping("/info")
    public ResponseEntity<?> UpdateInfo(@RequestBody MemberDto memberDto){
        MemberDto result = memberService.updateMember(memberDto);
        if(result == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<MemberDto>(result, HttpStatus.OK);
    }

    @DeleteMapping("/info")
    public ResponseEntity<?> DeleteInfo(@RequestBody MemberRequestDto requestDto){
        boolean result = memberService.disableMember(requestDto.getEmail());
        if(result) new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/passwordcheck")
    public ResponseEntity<?> PasswordCheck(){
        return null;
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> RefreshToken(){
        return null;
    }

}
