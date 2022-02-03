package hello.vuetilserver.service;

import hello.vuetilserver.config.security.JwtTokenProvider;
import hello.vuetilserver.controller.exception.MemberChangePasswordNotMatchException;
import hello.vuetilserver.controller.exception.MemberNotExistException;
import hello.vuetilserver.controller.exception.MemberPasswordNotMatchException;
import hello.vuetilserver.controller.exception.UnAuthorizedAccessException;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.dto.MemberLoginDto;
import hello.vuetilserver.domain.dto.MemberDto;
import hello.vuetilserver.domain.dto.MemberLoginSuccessDto;
import hello.vuetilserver.domain.dto.MemberUpdateDto;
import hello.vuetilserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberDto memberDto) {
        log.info("회원 저장 메소드 실행");
        memberRepository.save(Member.builder()
                .username(memberDto.getUsername())
                .password(passwordEncoder.encode((memberDto.getPassword())))
                .nickname(memberDto.getNickname())
                .roles(Collections.singletonList("USER")) //최초 가입시 USER로 설정
                .build());
    }

    public List<Member> memberList() {
        log.info("회원 전체 조회 메서드 실행");
        return memberRepository.findAll();
    }

    public Member findMember(MemberLoginDto memberLoginDto) {
        log.info("회원 단건 조회 메서드 실행");
        return memberRepository.findMemberByUsername(memberLoginDto.getUsername()).orElseThrow(() -> new MemberNotExistException("존재하지 않는 회원입니다."));
    }

    public Map<String, Object> login(MemberLoginDto memberLoginDto) {
        Map<String, Object> result = new HashMap<>();
        Member findMember = findMember(memberLoginDto);
        if (!passwordEncoder.matches(memberLoginDto.getPassword(), findMember.getPassword())) {
            throw new MemberPasswordNotMatchException("잘못된 비밀번호 입니다.");
        }
        MemberLoginSuccessDto memberLoginSuccessDto = new MemberLoginSuccessDto(findMember);
        result.put("user", memberLoginSuccessDto);
        result.put("token", jwtTokenProvider.createToken(findMember.getUsername(), findMember.getRoles()));

        return result;
    }

    public List<MemberLoginSuccessDto> findAllMembers() {
        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(m -> new MemberLoginSuccessDto(m.getUsername(), m.getNickname()))
                .collect(Collectors.toList());
    }

    public MemberLoginSuccessDto findMemberById(String id, Member member) {
        if (Long.parseLong(id) != member.getId()) {
            throw new UnAuthorizedAccessException("로그인 맴버와 조회 맴버가 다릅니다.");
        }
        Member foundedMember = memberRepository.findById(Long.parseLong(id)).orElseThrow(() -> new MemberNotExistException("존재하지 않는 맴버입니다."));

        MemberLoginSuccessDto memberLoginSuccessDto = new MemberLoginSuccessDto(foundedMember);
        return memberLoginSuccessDto;
    }

    public MemberLoginSuccessDto findMemberByUsername(String username) {
        Member foundedMember = memberRepository.findMemberByUsername(username).orElseThrow(() -> new MemberNotExistException("존재하지 않는 맴버입니다."));

        MemberLoginSuccessDto memberLoginSuccessDto = new MemberLoginSuccessDto(foundedMember);
        return memberLoginSuccessDto;
    }

    @Transactional
    public MemberLoginSuccessDto updateMember(String username, MemberUpdateDto memberUpdateDto, Member member) {
        Member foundedMember = memberRepository.findMemberByUsername(username).orElseThrow(() -> new MemberNotExistException("존재하지 않는 맴버입니다."));
        if (member.getId() == null) {
            throw new MemberNotExistException("없는 회원입니다.");
        }

        if (!passwordEncoder.matches(memberUpdateDto.getCurrentPassword(), foundedMember.getPassword())) {
            throw new MemberPasswordNotMatchException("현재 비밀번호가 다릅니다");
        }

        log.info("password" + memberUpdateDto.getChangePassword());
        log.info("password" + memberUpdateDto.getChangePasswordCheck());
        log.info("password" + memberUpdateDto.getChangePasswordCheck().equals(memberUpdateDto.getChangePassword()));
        log.info("password" + (memberUpdateDto.getChangePassword() != memberUpdateDto.getChangePasswordCheck()));


        if (!memberUpdateDto.getChangePassword().equals(memberUpdateDto.getChangePasswordCheck())) {
            throw new MemberChangePasswordNotMatchException("바꾸려는 비밀번호가 다릅니다.");
        }

        foundedMember.update(memberUpdateDto.getUsername(), passwordEncoder.encode((memberUpdateDto.getChangePassword())), memberUpdateDto.getNickname());

        MemberLoginSuccessDto memberLoginSuccessDto = new MemberLoginSuccessDto(foundedMember);

        return memberLoginSuccessDto;
    }

    @Transactional
    public MemberLoginSuccessDto deleteMember(String username, Member member) {
        if (member.getId() == null) {
            throw new MemberNotExistException("없는 회원입니다.");
        }

        Member foundedMember = memberRepository.findMemberByUsername(username).orElseThrow(() -> new MemberNotExistException("없는 회원입니다."));

        memberRepository.delete(foundedMember);
        MemberLoginSuccessDto memberLoginSuccessDto = new MemberLoginSuccessDto(foundedMember);

        return memberLoginSuccessDto;
    }
}
