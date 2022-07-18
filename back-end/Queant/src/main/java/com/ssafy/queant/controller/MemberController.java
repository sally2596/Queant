package com.ssafy.queant.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/member/")
public class MemberController {

    @PostMapping("signup")
    public void signUp(){
        log.info("signup");
    }
}
