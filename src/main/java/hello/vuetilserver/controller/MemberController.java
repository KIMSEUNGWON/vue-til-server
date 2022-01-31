package hello.vuetilserver.controller;

import hello.vuetilserver.api.Result;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.dto.MemberLoginDto;
import hello.vuetilserver.domain.dto.MemberDto;
import hello.vuetilserver.domain.dto.MemberLoginSuccessDto;
import hello.vuetilserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
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
//        Member findMember = memberService.findMember(memberLoginDto);
//        MemberLoginSuccessDto memberLoginSuccessDto = new MemberLoginSuccessDto(findMember);
        Map<String, Object> findMemberAndTokenString = memberService.login(memberLoginDto);

//        HttpHeaders httpHeaders = new HttpHeaders();

//        Map<String, Object> result = new HashMap<>();
//        result.put("user", memberLoginSuccessDto);
//        result.put("token", findMember.getToken());
//        httpHeaders.add("Authorization", findMember.getToken());

//        return result;
        return findMemberAndTokenString;
    }
}
