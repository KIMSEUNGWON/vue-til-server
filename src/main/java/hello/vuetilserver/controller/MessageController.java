package hello.vuetilserver.controller;

import hello.vuetilserver.api.Result;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.dto.MessagesCreateDto;
import hello.vuetilserver.domain.dto.MessagesDto;
import hello.vuetilserver.service.MemberService;
import hello.vuetilserver.service.MessagesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
@Slf4j
public class MessageController {

    private final MessagesService messagesService;
    private final MemberService memberService;

    @GetMapping("/")
    public Result messagesList(@AuthenticationPrincipal Member member) {
        log.info("member = " + member.getUsername());

        List<MessagesDto> allMessage = messagesService.getAllMessage(member);
        return new Result<>(allMessage);
    }

    @GetMapping("/received")
    public Result messagesReceivedList(@AuthenticationPrincipal Member member) {
        log.info("member = " + member.getUsername());

        List<MessagesDto> allMessage = messagesService.getAllReceivedMessage(member);
        return new Result<>(allMessage);
    }

    @PostMapping("/add")
    public MessagesDto messagesAdd(@RequestBody MessagesCreateDto messagesCreateDto, @AuthenticationPrincipal Member member) {
        log.info("messageCreateDto = " + messagesCreateDto.getTitle());

        MessagesDto messagesDto = messagesService.save(messagesCreateDto, member);

        return messagesDto;
    }

    @GetMapping("/{messagesId}")
    public MessagesDto messagesGetOne(@PathVariable Long messagesId, @AuthenticationPrincipal Member member) {
        log.info("messagesId = " + messagesId);

        MessagesDto messagesDto = messagesService.getOneMessage(messagesId, member);

        return messagesDto;
    }

    @DeleteMapping("/{messagesId}")
    public String messagesDelete(@PathVariable Long messagesId, @AuthenticationPrincipal Member member) {
        log.info("messagesId = " + messagesId);

        messagesService.deleteMessage(messagesId, member);

        return "success";
    }
}
