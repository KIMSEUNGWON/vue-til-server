package hello.vuetilserver.service;

import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.dto.MemberLoginDto;
import hello.vuetilserver.domain.dto.MemberDto;
import hello.vuetilserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberDto memberDto) {
        Member member = new Member(memberDto);
        log.info("회원 저장 메소드 실행");
        memberRepository.save(member);
    }

    public List<Member> memberList() {
        log.info("회원 전체 조회 메서드 실행");
        return memberRepository.findAll();
    }

    public Member findMember(MemberLoginDto memberLoginDto) {
        log.info("회원 단건 조회 메서드 실행");
        return memberRepository.findMemberByUsername(memberLoginDto.getUsername()).get();
    }

    public Member findMemberByToken(String token) {
        log.info("토큰을 이용한 회원 단건 조회 메서드 실행");
        return memberRepository.findMemberByToken(token).get();
    }
}
