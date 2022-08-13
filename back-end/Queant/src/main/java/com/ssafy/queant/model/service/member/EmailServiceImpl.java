package com.ssafy.queant.model.service.member;

import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.repository.MemberRepository;
import com.ssafy.queant.model.service.member.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:application-mail.properties")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
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
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요.<p>";
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
    public MimeMessage createPassword(String to, String ePw) throws Exception {
        log.info("보내는 대상 : {}",to);
        log.info("인증 번호 : {}",ePw);
        MimeMessage  message = emailSender.createMimeMessage();


        message.addRecipients(Message.RecipientType.TO, to);//보내는 대상
        message.setSubject("Queant 임시 비밀번호가 도착했습니다.");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> Queant </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 임시 비밀번호로 로그인 후 비밀번호를 변경해주세요.<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>임시 비밀번호입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "PASSWORD : <strong>";
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
    public boolean sendPassword(String to) throws Exception {
        //새 비밀번호 생성
        String ePw = EmailService.createPassword();
        Optional<Member> result = memberRepository.findByEmail(to);
        if(!result.isPresent()) return false;

        //DB 비밀번호 변경
        Member member = result.get();
        member.setPassword(passwordEncoder.encode(ePw));
        memberRepository.save(member);

        //이메일 생성 후 발송
        MimeMessage message = createPassword(to, ePw);
        try{
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return true;
    }


    @Override
    public boolean verifyCode(String code) throws Exception {
        if(!new Date().before(expiration)) return false;
        return code.equals(ePw);
    }
}
