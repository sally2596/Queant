package com.ssafy.queant.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:application-mail.properties")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private static final String ePw = EmailService.createKey();
    @Value("${AdminMail.id}")
    private String provider;
    private Long time = 1000L*60*5;
    private Date expiration;

    @Override
    public MimeMessage createMessage(String to) throws Exception {
        log.info("보내는 대상 : {}",to);
        log.info("인증 번호 : {}",ePw);
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);//보내는 대상
        message.setSubject("Queant 인증번호가 도착했습니다.");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> Queant </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress(provider,"Queant"));//보내는 사람

        return message;
    }

    @Override
    public void sendMessage(String to) throws Exception {
        MimeMessage message = createMessage(to);
        try{
            expiration = new Date(new Date().getTime() + time);
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }

    }

    @Override
    public boolean verifyCode(String code) throws Exception {
        if(!new Date().before(expiration)) return false;
        return code.equals(ePw);
    }
}
