package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.LoginResultDto;
import com.ssafy.queant.model.dto.MemberDto;
import com.ssafy.queant.model.dto.MemberResponseDto;
import com.ssafy.queant.model.entity.Member;
import com.ssafy.queant.model.entity.MemberRole;
import com.ssafy.queant.model.entity.Social;
import com.ssafy.queant.model.repository.MemberRepository;
import com.ssafy.queant.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, ModelMapper modelMapper){
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean register(MemberDto memberDto) throws RuntimeException{
        log.info("[register] 회원가입 정보 전달");
        log.info("[register] 비밀번호 : {}", memberDto.getPassword());
        Member member = modelMapper.map(memberDto, Member.class);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRoleSet(new HashSet<>());
        member.addMemberRole(MemberRole.ROLE_USER);
        member.setSocial(Social.None);
        Member savedMember = memberRepository.save(member);

        if(!savedMember.getName().isEmpty()){
            log.info("[register] : 회원가입 완료");
            return true;
        } else {
            log.info("[register] : 회원가입 실패");
            return false;
        }
    }

    @Override
    public LoginResultDto login(String email, String password) throws RuntimeException {
        Optional<Member> result = memberRepository.findByEmail(email);
        LoginResultDto loginResultDto = new LoginResultDto();
        if(!result.isPresent()) {
            log.info("[login] : 존재하지 않는 이메일입니다.");
            loginResultDto.setResult("EmailError");
            return loginResultDto;
        }
        Member member = result.get();
        if(!passwordEncoder.matches(password, member.getPassword())) {
            log.info("[login] : 패스워드가 일치하지 않습니다.");
            loginResultDto.setResult("PasswordError");
            return loginResultDto;
        }

        if(!member.isEnabled()){
            log.info("[login] : 휴면계정입니다.");
            loginResultDto.setResult("Disabled");
            return loginResultDto;
        }

        log.info("[login] 로그인 성공!");
        //RefreshToken 주입
        loginResultDto.setResult("SUCCESS");
        String refreshToken = jwtTokenProvider.createRefreshToken();
        loginResultDto.setRefreshToken(refreshToken);
        member.setRefreshToken(refreshToken);
        memberRepository.save(member);

        return loginResultDto;

    }

    @Override
    public boolean emailCheck(String email) throws RuntimeException{
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.isPresent();
    }

    @Override
    public MemberDto findMember(String email) throws RuntimeException{
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()){
            MemberDto memberDto = modelMapper.map(member.get(), MemberDto.class);
            return memberDto;
        }
        return null;
    }

    @Override
    public MemberDto updateMember(MemberDto memberDto) throws RuntimeException{
        Optional<Member> result = memberRepository.findByEmail(memberDto.getEmail());
        Member member = result.get();
        if(memberDto.getGender() != member.getGender()) member.setGender(memberDto.getGender());
        if(memberDto.getBirthdate() != member.getBirthdate()) member.setBirthdate(memberDto.getBirthdate());
        if(memberDto.getName() != member.getName()) member.setName(memberDto.getName());

        memberRepository.save(member);
        result = memberRepository.findByEmail(memberDto.getEmail());
        memberDto = modelMapper.map(result.get(), MemberDto.class);
        return memberDto;
    }

    @Override
    public boolean disableMember(String email) throws RuntimeException{
        Optional<Member> result = memberRepository.findByEmail(email);
        if(!result.isPresent()) return false;
        Member member = result.get();
        member.setEnabled(!member.isEnabled());
        memberRepository.save(member);
        return true;
    }

    @Override
    public boolean changePassword(String email, String password) throws RuntimeException {
        Optional<Member> result = memberRepository.findByEmail(email);
        if(!result.isPresent()) return false;
        Member member = result.get();
        member.setPassword(password);
        memberRepository.save(member);
        return true;
    }

    @Override
    public MemberDto updateRoles(String email, Set<MemberRole> roleSet) throws RuntimeException {
        Optional<Member> result = memberRepository.findByEmail(email);
        if(!result.isPresent()) return null;
        Member member = result.get();

        member.setRoleSet(new HashSet<>());
        for(MemberRole role : roleSet){
            member.addMemberRole(role);
        }

        memberRepository.save(member);
        return modelMapper.map(member, MemberDto.class);
    }

    @Override
    public MemberResponseDto memberListByRoles(MemberRole role, int page) throws RuntimeException {
        //Repository 결과
        Pageable pageable = PageRequest.of(page-1, 10);
        Page<Member> result = memberRepository.findByRole(role, pageable);

        //list를 MemberDto로 변환
        MemberResponseDto memberResponseDto = new MemberResponseDto();
        List<MemberDto> memberlist = new ArrayList<>();
        result.get().forEach(member -> memberlist.add(modelMapper.map(member, MemberDto.class)));

        //memberResponseDto에 포장
        memberResponseDto.setMemberDtoList(memberlist);
        memberResponseDto.setTotalPage(result.getTotalPages());
        return memberResponseDto;
    }

    @Override
    public MemberResponseDto memberListBySocial(Social social, int page) throws RuntimeException {
        Pageable pageable = PageRequest.of(page-1, 10);
        Page<Member> result = memberRepository.findBySocial(social, pageable);

        MemberResponseDto memberResponseDto = new MemberResponseDto();
        List<MemberDto> memberlist = new ArrayList<>();
        result.get().forEach(member -> memberlist.add(modelMapper.map(member, MemberDto.class)));

        memberResponseDto.setMemberDtoList(memberlist);
        memberResponseDto.setTotalPage(result.getTotalPages());
        return memberResponseDto;
    }


}
