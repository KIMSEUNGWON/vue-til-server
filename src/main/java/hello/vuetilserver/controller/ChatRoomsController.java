package hello.vuetilserver.controller;

import hello.vuetilserver.api.Result;
import hello.vuetilserver.controller.exception.ChatRoomsDuplicatedParticipantException;
import hello.vuetilserver.domain.ChatMessages;
import hello.vuetilserver.domain.ChatRooms;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.dto.*;
import hello.vuetilserver.service.ChatRoomsService;
import hello.vuetilserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatRooms")
@Slf4j
public class ChatRoomsController {

    private final ChatRoomsService chatRoomsService;
    private final MemberService memberService;

    @GetMapping("/")
    public Result chatRoomsList(@AuthenticationPrincipal Member member,
                                @RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "10") Integer size) {

        log.info("page = " + page);
        log.info("size = " + size);

        List<ChatRoomsListDto> result = chatRoomsService.getAllChatRoomsWithPaging(member, page, size);

        return new Result<>(result);
    }

    @PostMapping("/create")
    public ChatRoomsCreateDto chatRoomsCreate(@RequestBody ChatRoomsCreateDto chatRoomsCreateDto,
                                              @AuthenticationPrincipal Member member) {
        log.info("chatRoomsCreateDto = " + chatRoomsCreateDto.getTitle());

        ChatRooms chatRooms = chatRoomsService.create(chatRoomsCreateDto, member);

        ChatRoomsCreateDto result = new ChatRoomsCreateDto(chatRooms.getTitle());
        return result;
    }

    @PostMapping("/participant/{chatRoomsId}")
    public Result chatRoomsParticipant(@PathVariable("chatRoomsId") Long chatRoomsId,
                                       @AuthenticationPrincipal Member member) {
        log.info("chatRoomsId = " + chatRoomsId);

        if (chatRoomsService.duplicateParticipant(chatRoomsId, member)) {
            throw new ChatRoomsDuplicatedParticipantException("중복된 참여입니다.");
        }

        ChatRooms chatRooms = chatRoomsService.participant(chatRoomsId, member);
//        ChatRoomsListDto chatRoomsListDto = new ChatRoomsListDto(chatRooms);
        List<ChatRoomsEnterListDto> result = chatRoomsService.getAllMemberEnteredChatRooms(chatRooms.getId());

        return new Result<>(result);
    }

    @PostMapping("/addChatMessages/{chatRoomsId}")
    public Result chatRoomsAddChatMessages(@PathVariable("chatRoomsId") Long chatRoomsId,
                                           @RequestBody ChatMessagesCreateDto chatMessagesCreateDto,
                                           @AuthenticationPrincipal Member member) {
        log.info("chatRoomsId = " + chatRoomsId);
        log.info("chatMessages = " + chatMessagesCreateDto.getSender());

        ChatMessages chatMessages = ChatMessages.builder().
                type(chatMessagesCreateDto.getType())
                .content(chatMessagesCreateDto.getContent())
                .sender(chatMessagesCreateDto.getSender())
                .createdAt(LocalDateTime.now())
                .build();

        ChatRooms chatRooms = chatRoomsService.addChatMessages(chatRoomsId, chatMessages, member);
        List<ChatMessagesListDto> result = chatRooms.getChatMessages().stream()
                .map(c -> new ChatMessagesListDto(c))
                .collect(Collectors.toList());

        return new Result<>(result);
    }

    @GetMapping("/{chatRoomsId}")
    public Result chatRoomsGetOne(@PathVariable Long chatRoomsId,
                                  @AuthenticationPrincipal Member member) {
        log.info("chatRoomsId = " + chatRoomsId);

        ChatRooms chatRooms = chatRoomsService.getOneChatRoomWithMember(chatRoomsId, member);
        Map<String, Object> result = new HashMap<>();
        result.put("title", chatRooms.getTitle());
        result.put("lastChattedAt", chatRooms.getLastChattedAt());
        result.put("chatRoomsEnterList.",
                chatRooms.getChatRoomsEnterList().stream().map(
                        (enteredMember) -> {
                            String enterMemberUsername = enteredMember.getMember().getUsername();
                            return enterMemberUsername;
                        }
                ).collect(Collectors.toList()));

        return new Result<>(result);
    }
}
