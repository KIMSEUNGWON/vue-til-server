package hello.vuetilserver.controller;

import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.dto.MemberDto;
import hello.vuetilserver.domain.dto.MemberLoginDto;
import hello.vuetilserver.domain.dto.MemberLoginSuccessDto;
import hello.vuetilserver.domain.dto.MemberUpdateDto;
import hello.vuetilserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public MemberDto signup(@RequestBody MemberDto memberDto) {
        log.info("memberDto = " + memberDto.getUsername());
        memberService.save(memberDto);
        return memberDto;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody MemberLoginDto memberLoginDto) {
        log.info("memberLoginDto = " + memberLoginDto.getUsername());

        Map<String, Object> findMemberAndTokenString = memberService.login(memberLoginDto);

        return findMemberAndTokenString;
    }

    @GetMapping
    public List<MemberLoginSuccessDto> memberList(@AuthenticationPrincipal Member member) {
        log.info("회원가입한 멤버 전체 조회 메서드");

        return memberService.findAllMembers();
    }

    @GetMapping("/{username}")
    public MemberLoginSuccessDto getMember(@PathVariable("username") String username, @AuthenticationPrincipal Member member) {
        log.info("특정 맴버 조회 메서드");
        log.info("username = " + username);
        log.info("member = " + member.getId());
        log.info("member.hashCode = " + member.hashCode());

        MemberLoginSuccessDto result = memberService.findMemberByUsername(username);

        return result;
    }

    @PutMapping("/{username}")
    public MemberLoginSuccessDto updateMember(@PathVariable("username") String username,
                                              @RequestBody MemberUpdateDto memberUpdateDto,
                                              @AuthenticationPrincipal Member member) {
        log.info("맴버 업데이트 매서드");
        log.info("username = " + username);
        log.info("member = " + member.getId());
        log.info("member = " + member.hashCode());
        log.info("memberUpdateDto = " + memberUpdateDto.getUsername());

        MemberLoginSuccessDto result = memberService.updateMember(username, memberUpdateDto, member);

        return result;
    }

    @DeleteMapping("/{username}")
    public MemberLoginSuccessDto deleteMember(@PathVariable String username, @AuthenticationPrincipal Member member) {
        log.info("맴버 삭제 매서드");
        log.info("username = " + username);
        log.info("member = " + member.getId());
        log.info("member = " + member.hashCode());

        MemberLoginSuccessDto result = memberService.deleteMember(username, member);

        return result;
    }
}
