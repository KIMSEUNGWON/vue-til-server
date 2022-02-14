package hello.vuetilserver.service;

import hello.vuetilserver.controller.exception.ChatRoomsNotExistException;
import hello.vuetilserver.domain.ChatMessages;
import hello.vuetilserver.domain.ChatRooms;
import hello.vuetilserver.domain.ChatRoomsEnter;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.dto.ChatRoomsCreateDto;
import hello.vuetilserver.domain.dto.ChatRoomsEnterListDto;
import hello.vuetilserver.domain.dto.ChatRoomsListDto;
import hello.vuetilserver.repository.ChatRoomsRepository;
import hello.vuetilserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomsService {

    private final ChatRoomsRepository chatRoomsRepository;
    private final ChatRoomsEnterService chatRoomsEnterService;
    private final MemberRepository memberRepository;

    // 채팅방 생성 메서드
    @Transactional
    public ChatRooms create(ChatRoomsCreateDto chatRoomsCreateDto, Member member) {
        log.info("채팅방 생성 메서드");
        log.info("chatRoomsCreateDto = " + chatRoomsCreateDto.getTitle());
        log.info("member = " + member.getId());

        ChatRooms chatRooms = new ChatRooms(chatRoomsCreateDto);
        ChatRoomsEnter chatRoomsEnter = chatRoomsEnterService.enter(chatRooms, member);

        ChatRooms result = chatRoomsRepository.save(chatRooms);

        return result;
    }

    @Transactional
    public ChatRooms participant(Long chatRoomsId, Member member) {
        log.info("채팅방 참가 메서드");

        ChatRooms chatRooms = getOneChatRoom(chatRoomsId);
        if (chatRooms == null) {
            throw new ChatRoomsNotExistException("존재하지 않는 채팅방입니다.");
        }

        ChatRoomsEnter chatRoomsEnter = chatRoomsEnterService.enter(chatRooms, member);

        return chatRooms;
    }

    @Transactional
    public ChatRooms addChatMessages(Long chatRoomsId, ChatMessages chatMessages, Member member) {
        log.info("채팅을 저장하는 메서드");

        ChatRooms chatRooms = getOneChatRoomWithMember(chatRoomsId, member);
        if (chatRooms == null) {
            throw new ChatRoomsNotExistException("존재하지 않는 채팅방입니다.");
        }

        chatRooms.setChatMessages(chatMessages);

        return chatRooms;
    }

    public List<ChatRoomsListDto> getAllChatRoomsWithPaging(Member member, Integer page, Integer size) {
        log.info("맴버가 입장한 모든 채팅방 목록 조회 메서드");

        PageRequest pageRequest = PageRequest.of(page, size);

        List<ChatRooms> chatRoomsList = chatRoomsRepository.findAll(member, pageRequest).orElseThrow(() -> new ChatRoomsNotExistException("존재하지 않는 채팅방입니다."));

        System.out.println("test = " + chatRoomsList.get(0).getChatRoomsEnterList().size());

        List<ChatRoomsListDto> result = chatRoomsList.stream()
                .map(chatRooms -> new ChatRoomsListDto(chatRooms))
                .collect(Collectors.toList());

        for (ChatRooms chatRooms : chatRoomsList) {
            List<ChatRoomsEnter> allChatRoomsEnter = chatRoomsEnterService.getAllChatRoomsEnter(chatRooms);


        }

        return result;
    }

    public List<ChatRoomsEnterListDto> getAllMemberEnteredChatRooms(Long chatRoomsId) {
        log.info("채팅방에 입장한 모든 멤버 목록 조회 메서드");
        ChatRooms chatRooms = chatRoomsRepository.findById(chatRoomsId).orElseThrow(() -> new ChatRoomsNotExistException("존재하지 않는 채팅방입니다."));

        List<ChatRoomsEnterListDto> result = chatRooms.getChatRoomsEnterList().stream()
                .map(chatRoomsEnter -> new ChatRoomsEnterListDto(chatRoomsEnter))
                .collect(Collectors.toList());

        return result;
    }

    public ChatRooms getOneChatRoomWithMember(Long chatRoomsId, Member member) {
        log.info("특정 회원 특정 채팅방 목록 조회 메서드");

//        ChatRooms result = chatRoomsRepository.findById(chatRoomsId).orElseThrow(() -> new ChatRoomsNotExistException("존재하지 않는 채팅방입니다."));
        ChatRooms result = chatRoomsRepository.findByIdAndChatRoomsEnterListContains(chatRoomsId, member).orElse(null);

        return result;
    }

    public ChatRooms getOneChatRoom(Long chatRoomsId) {
        log.info("특정 채팅방 목록 조회 메서드");

        ChatRooms result = chatRoomsRepository.findById(chatRoomsId).orElseThrow(() -> new ChatRoomsNotExistException("존재하지 않는 채팅방입니다."));

        return result;
    }

    public boolean duplicateParticipant(Long chatRoomsId, Member member) {
        log.info("채팅방 중복 참여 조회 메서드");

        ChatRooms chatRooms = getOneChatRoomWithMember(chatRoomsId, member);

        if (chatRooms != null) {
            return true;
        }

        return false;
    }
}
