package com.ssafy.queant.controller;

import com.ssafy.queant.model.dto.LoginResultDto;
import com.ssafy.queant.model.dto.MemberDto;
import com.ssafy.queant.model.dto.MemberRequestDto;
import com.ssafy.queant.model.service.EmailService;
import com.ssafy.queant.model.service.MemberService;
import com.ssafy.queant.security.JwtTokenProvider;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @ApiResponses({
            @ApiResponse(code = 200, message="사용 가능한 이메일주소입니다."),
            @ApiResponse(code = 409, message="이미 존재하는 이메일입니다."),
    })
    @Operation(summary = "이메일 중복 체크", description = "가입 시 입력한 이메일이 DB에 존재하는지 확인하고 해당 이메일로 인증코드 발송")
    @PostMapping("/emailcheck")
    //200은 성공 409중복되었다.
    public ResponseEntity<?> emailCheck(@RequestBody MemberRequestDto requestDto) throws Exception {
        if(memberService.emailCheck(requestDto.getEmail())) return new ResponseEntity<>(HttpStatus.CONFLICT);
        emailService.sendMessage(requestDto.getEmail());
        log.info("[emailCheck] 이메일이 발송되었습니다. email : {}", requestDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="인증번호가 일치합니다."),
            @ApiResponse(code = 409, message="인증번호가 일치하지 않습니다."),
    })
    @ApiOperation(value="이메일 인증 코드 확인", notes="이메일 인증 코드가 일치하는지 확인")
    @PostMapping("/emailverify")
    public ResponseEntity<?> verifyCode(@RequestBody MemberRequestDto requestDto) throws Exception {

        log.info("[verifyCode] 입력받은 코드 : {}" , requestDto.getCode());
        if(emailService.verifyCode(requestDto.getCode())) return new ResponseEntity<>(HttpStatus.OK);
        else log.info("[verifyCode] 입력받은 코드가 일치하지 않습니다.");
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="로그인 성공!"),
            @ApiResponse(code = 404, message="이메일을 다시 확인해주세요."),
            @ApiResponse(code = 409, message="비밀번호가 일치하지 않습니다."),
    })
    @ApiOperation(value="로그인", notes="email, password 필수 입력")
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

    @ApiResponses({
            @ApiResponse(code = 200, message="회원가입에 성공하였습니다."),
            @ApiResponse(code = 409, message="회원가입에 실패하였습니다."),
    })
    @ApiOperation(value="회원가입", notes="")
    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody MemberDto memberDto){
        log.info("[Register] 정보 확인 비번 : {}" , memberDto.getPassword());
        if(memberService.register(memberDto)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="회원 정보 조회 성공"),
            @ApiResponse(code = 404, message="존재하는 회원이 아닙니다."),
    })
    @ApiOperation(value="회원 정보 조회", notes="email 필수")
    @PostMapping("/info")
    public ResponseEntity<?> Info(@RequestBody MemberRequestDto requestDto){
        MemberDto memberDto = memberService.findMember(requestDto.getEmail());
        if(memberDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="회원 정보 수정 성공"),
            @ApiResponse(code = 404, message="존재하는 회원이 아닙니다."),
    })
    @ApiOperation(value="회원 정보 수정", notes="")
    @PutMapping("/info")
    public ResponseEntity<?> UpdateInfo(@RequestBody MemberDto memberDto){
        MemberDto result = memberService.updateMember(memberDto);
        if(result == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        result.setPassword(null);
        result.setRefreshToken(null);
        return new ResponseEntity<MemberDto>(result, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="계정 비활성화 성공"),
            @ApiResponse(code = 409, message="계정 비활성화에 문제가 있습니다"),
    })
    @ApiOperation(value="회원 탈퇴", notes="email 필수 입력")
    @DeleteMapping("/info")
    public ResponseEntity<?> DeleteInfo(@RequestBody MemberRequestDto requestDto){
        boolean result = memberService.disableMember(requestDto.getEmail());
        if(result) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="비밀번호가 일치합니다."),
            @ApiResponse(code = 404, message="존재하는 회원이 아닙니다."),
            @ApiResponse(code = 409, message="비밀번호가 일치하지 않습니다."),
    })
    @ApiOperation(value="비밀번호 확인", notes="email, password 입력")
    @PostMapping("/passwordcheck")
    public ResponseEntity<?> PasswordCheck(@RequestBody MemberRequestDto requestDto){
        MemberDto member = memberService.findMember(requestDto.getEmail());
        if(member == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="AccessToken 재발급 완료."),
            @ApiResponse(code = 404, message="존재하는 회원이 아닙니다."),
            @ApiResponse(code = 409, message="RefreshToken이 일치하지 않습니다."),
            @ApiResponse(code = 406, message="RefreshToken 만료. 로그인이 필요합니다."),
    })
    @ApiOperation(value="AccessToken 재발급", notes="AccessToken이 만료되었을 때, refreshtoken을 통해 AccessToken 재발급. email, refreshtoken 입력")
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> RefreshToken( @RequestBody MemberRequestDto requestDto){
        MemberDto memberDto = memberService.findMember(requestDto.getEmail());
        if(memberDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //유효한 refreshToken
        if(jwtTokenProvider.validateToken(requestDto.getRefreshtoken())){
            //refreshToken 일치 -> accessToken 새로 발급
            if(memberDto.getRefreshToken().equals(requestDto.getRefreshtoken())){
                String AccessToken = jwtTokenProvider.createToken(memberDto.getEmail());
                return new ResponseEntity<String>(AccessToken,HttpStatus.OK);
            } else { //refreshToken 불일치
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } else { //refreshToken 만료 시 로그인 진행
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
