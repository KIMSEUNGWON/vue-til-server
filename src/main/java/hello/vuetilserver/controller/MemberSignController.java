package hello.vuetilserver.controller;

import hello.vuetilserver.domain.dto.MemberLoginDto;
import hello.vuetilserver.domain.dto.MemberDto;
import hello.vuetilserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MemberSignController {

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
}
