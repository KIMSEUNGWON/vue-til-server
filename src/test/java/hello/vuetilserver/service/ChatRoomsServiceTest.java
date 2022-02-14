package hello.vuetilserver.service;

import hello.vuetilserver.domain.ChatMessages;
import hello.vuetilserver.domain.ChatRooms;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.MessageType;
import hello.vuetilserver.domain.dto.ChatRoomsCreateDto;
import hello.vuetilserver.domain.dto.ChatRoomsEnterListDto;
import hello.vuetilserver.domain.dto.ChatRoomsListDto;
import hello.vuetilserver.domain.dto.MemberLoginDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//@Rollback(false)
class ChatRoomsServiceTest {

    @Autowired
    private ChatRoomsService chatRoomsService;
    @Autowired MemberService memberService;

    @Test
    void createTest() {
        //given
        Member member = memberService.findMember(new MemberLoginDto("a@a.com", "123"));
        ChatRoomsCreateDto chatRoomsCreateDto = new ChatRoomsCreateDto("chatRoom1");

        //when
        ChatRooms chatRooms = chatRoomsService.create(chatRoomsCreateDto, member);

        //then
        assertThat(chatRooms.getTitle()).isEqualTo(chatRoomsCreateDto.getTitle());
        assertThat(chatRooms.getChatRoomsEnterList().size()).isEqualTo(1);
        assertThat(chatRooms.getChatRoomsEnterList().get(0).getMember()).isEqualTo(member);
    }

    @Test
    void getAllChatRoomsWithPagingTest() {
        //given
        Member member = memberService.findMember(new MemberLoginDto("a@a.com", "123"));
        Member member2 = memberService.findMember(new MemberLoginDto("c@c.com", "123"));

        int page = 0;
        int size = 10;

        //when
        List<ChatRoomsListDto> allChatRoomsEnterWithPaging = chatRoomsService.getAllChatRoomsWithPaging(member, page, size);


        //then
//        assertThat(allChatRoomsEnterWithPaging.size()).isEqualTo(size);
    }

    @Test
    void getAllMemberEnteredChatRoomsTest() {
        //given
        Member member = memberService.findMember(new MemberLoginDto("a@a.com", "123"));
        Member member2 = memberService.findMember(new MemberLoginDto("c@c.com", "123"));

        //when
        List<ChatRoomsEnterListDto> allMemberEnteredChatRooms = chatRoomsService.getAllMemberEnteredChatRooms(10L);

        //then
        assertThat(allMemberEnteredChatRooms.get(0).getUsername()).isEqualTo(member.getUsername());
        assertThat(allMemberEnteredChatRooms.get(1).getUsername()).isEqualTo(member2.getUsername());
    }

    @Test
    void participantTest() {
        //given
        Member createChatRoomMember = memberService.findMember(new MemberLoginDto("a@a.com", "123"));
        ChatRooms chatRooms = chatRoomsService.create(new ChatRoomsCreateDto("chatRoom"), createChatRoomMember);
        Member participantChatRoomMember = memberService.findMember(new MemberLoginDto("c@c.com", "123"));

        //when
        ChatRooms result = chatRoomsService.participant(chatRooms.getId(), participantChatRoomMember);

        //then
        assertThat(result.getTitle()).isEqualTo(chatRooms.getTitle());
        assertThat(result).isEqualTo(chatRooms);
        assertThat(result.getChatRoomsEnterList().get(0).getMember()).isEqualTo(createChatRoomMember);
        assertThat(result.getChatRoomsEnterList().get(1).getMember()).isEqualTo(participantChatRoomMember);

    }

    @Test
    void addChatMessagesTest() {
        //given
        Member createChatRoomMember = memberService.findMember(new MemberLoginDto("a@a.com", "123"));
        ChatRooms chatRooms = chatRoomsService.create(new ChatRoomsCreateDto("chatRoom"), createChatRoomMember);
        Member participantChatRoomMember = memberService.findMember(new MemberLoginDto("c@c.com", "123"));
        chatRooms = chatRoomsService.participant(chatRooms.getId(), participantChatRoomMember);
        ChatMessages chatMessages = new ChatMessages(MessageType.CHAT, createChatRoomMember.getUsername());

        //when
        ChatRooms result = chatRoomsService.addChatMessages(chatRooms.getId(), chatMessages, createChatRoomMember);

        //then
        assertThat(result.getChatMessages().size()).isEqualTo(1);
        assertThat(result.getChatMessages().get(0).getType()).isEqualTo(MessageType.CHAT);
        assertThat(result.getChatMessages().get(0).getSender()).isEqualTo(createChatRoomMember.getUsername());
    }
}