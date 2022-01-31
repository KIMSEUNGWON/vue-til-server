package hello.vuetilserver.service;

import hello.vuetilserver.config.security.JwtTokenProvider;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.dto.MemberLoginDto;
import hello.vuetilserver.domain.dto.MemberDto;
import hello.vuetilserver.domain.dto.MemberLoginSuccessDto;
import hello.vuetilserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberDto memberDto) {
        Member member = new Member(memberDto);
        log.info("회원 저장 메소드 실행");
        memberRepository.save(Member.builder()
                .username(memberDto.getUsername())
                .password(passwordEncoder.encode((memberDto.getPassword())))
                .roles(Collections.singletonList("ROLE_USER")) //최초 가입시 USER로 설정
                .build());
    }

    public List<Member> memberList() {
        log.info("회원 전체 조회 메서드 실행");
        return memberRepository.findAll();
    }

    public Member findMember(MemberLoginDto memberLoginDto) {
        log.info("회원 단건 조회 메서드 실행");
        return memberRepository.findMemberByUsername(memberLoginDto.getUsername()).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-Mail 입니다"));
    }

    public Map<String, Object> login(MemberLoginDto memberLoginDto) {
        Map<String, Object> result = new HashMap<>();
        Member findMember = findMember(memberLoginDto);
        if (!passwordEncoder.matches(memberLoginDto.getPassword(), findMember.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        MemberLoginSuccessDto memberLoginSuccessDto = new MemberLoginSuccessDto(findMember);
        result.put("user", memberLoginSuccessDto);
        result.put("token", jwtTokenProvider.createToken(findMember.getUsername(), findMember.getRoles()));

        return result;
    }

    public Member findMemberByToken(String token) {
        log.info("토큰을 이용한 회원 단건 조회 메서드 실행");
        return memberRepository.findMemberByToken(token).get();
    }
}
