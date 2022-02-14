package hello.vuetilserver.service;

import hello.vuetilserver.controller.exception.ChatRoomsEnterNotExistException;
import hello.vuetilserver.domain.ChatRooms;
import hello.vuetilserver.domain.ChatRoomsEnter;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.repository.ChatRoomsEnterRepository;
import hello.vuetilserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomsEnterService {

    private final ChatRoomsEnterRepository chatRoomsEnterRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ChatRoomsEnter enter(ChatRooms chatRooms, Member member) {
        log.info("채팅방 입장 메서드");

        ChatRoomsEnter chatRoomsEnter = new ChatRoomsEnter(member, chatRooms);
        ChatRoomsEnter result = chatRoomsEnterRepository.save(chatRoomsEnter);

        return result;
    }

    public List<ChatRoomsEnter> getAllChatRoomsEnter(Member member) {
        log.info("모든 입장한 방 조회 메서드");

        List<ChatRoomsEnter> result = chatRoomsEnterRepository.findAll(member).orElseThrow(() -> new ChatRoomsEnterNotExistException("입장하지 않은 채팅방입니다."));

        return result;
    }

    public List<ChatRoomsEnter> getAllChatRoomsEnter(ChatRooms chatRooms) {
        log.info("특정 채팅방에 입장한 모든 멤버 조회 메서드");

        List<ChatRoomsEnter> allByChatRooms = chatRoomsEnterRepository.findAllByChatRooms(chatRooms);

        return allByChatRooms;
    }

    public ChatRoomsEnter getOneChatRoomsEnterById(Member member, Long chatRoomsEnterId) {
        log.info("특정 입장한 방 조회 메서드");

        ChatRoomsEnter result = chatRoomsEnterRepository.findById(chatRoomsEnterId).orElseThrow(() -> new ChatRoomsEnterNotExistException("입장하지 않은 채팅방입니다."));

        return result;
    }
}
