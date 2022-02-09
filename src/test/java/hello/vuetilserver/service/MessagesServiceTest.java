package hello.vuetilserver.service;

import hello.vuetilserver.controller.exception.MessageNotExistException;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.Messages;
import hello.vuetilserver.domain.dto.MemberLoginDto;
import hello.vuetilserver.domain.dto.MessagesCreateDto;
import hello.vuetilserver.domain.dto.MessagesDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@WebAppConfiguration
class MessagesServiceTest {
    @Autowired
    MessagesService messagesService;
    @Autowired
    MemberService memberService;

    @Test
    void saveTest() {
        //given
        Member sender = memberService.findMember(new MemberLoginDto("a@a.com", "123"));
        Member receiver = memberService.findMember(new MemberLoginDto("c@c.com", "123"));
        MessagesCreateDto messagesCreateDto = new MessagesCreateDto(sender.getUsername(), receiver.getUsername(), "title", "contents");

        //when
        MessagesDto messagesDto = messagesService.save(messagesCreateDto, sender);

        //then
        assertThat(messagesDto.getSender()).isEqualTo(sender.getUsername());
        assertThat(messagesDto.getTitle()).isEqualTo(messagesCreateDto.getTitle());
    }

    @Test
    void getAllMessageTest() {
        //given
        Member sender = memberService.findMember(new MemberLoginDto("a@a.com", "123"));
        Member receiver = memberService.findMember(new MemberLoginDto("c@c.com", "123"));
        Member receiver2 = memberService.findMember(new MemberLoginDto("b@b.com", "123"));
        Member receiver3 = memberService.findMember(new MemberLoginDto("d@d.com", "123"));
        MessagesCreateDto messagesCreateDto = new MessagesCreateDto(sender.getUsername(), receiver.getUsername(), "title", "contents");
        messagesService.save(messagesCreateDto, sender);
        messagesCreateDto = new MessagesCreateDto(sender.getUsername(), receiver2.getUsername(), "title2", "contents3");
        messagesService.save(messagesCreateDto, sender);
        messagesCreateDto = new MessagesCreateDto(sender.getUsername(), receiver3.getUsername(), "title2", "contents3");
        messagesService.save(messagesCreateDto, sender);

        //when
        List<MessagesDto> allMessage = messagesService.getAllMessage(sender);

        //then
        assertThat(allMessage.size()).isEqualTo(3);
    }

    @Test
    void getAllReceivedMessageTest() {
        //given
        Member receiver = memberService.findMember(new MemberLoginDto("a@a.com", "123"));
        Member sender = memberService.findMember(new MemberLoginDto("c@c.com", "123"));
        Member sender2 = memberService.findMember(new MemberLoginDto("b@b.com", "123"));
        Member sender3 = memberService.findMember(new MemberLoginDto("d@d.com", "123"));
        MessagesCreateDto messagesCreateDto = new MessagesCreateDto(sender.getUsername(), receiver.getUsername(), "title", "contents");
        messagesService.save(messagesCreateDto, sender);
        messagesCreateDto = new MessagesCreateDto(sender2.getUsername(), receiver.getUsername(), "title2", "contents3");
        messagesService.save(messagesCreateDto, sender);
        messagesCreateDto = new MessagesCreateDto(sender3.getUsername(), receiver.getUsername(), "title2", "contents3");
        messagesService.save(messagesCreateDto, sender);

        //when
        List<MessagesDto> allMessage = messagesService.getAllReceivedMessage(receiver);

        //then
        assertThat(allMessage.size()).isEqualTo(3);
    }

    @Test
    void getAllMessageEmptyTest() {
        //given
        Member sender = memberService.findMember(new MemberLoginDto("a@a.com", "123"));

        //when
        List<MessagesDto> allMessage = messagesService.getAllMessage(sender);

        //then
        assertThat(allMessage).isEmpty();
    }

    @Test
    void getOneMessageTest() {
        //given
        Member sender = memberService.findMember(new MemberLoginDto("a@a.com", "123"));
        Member receiver = memberService.findMember(new MemberLoginDto("c@c.com", "123"));
        MessagesCreateDto messagesCreateDto = new MessagesCreateDto(sender.getUsername(), receiver.getUsername(), "title", "contents");
        Messages messages = messagesService.saveForTest(messagesCreateDto, sender);

        //when
        MessagesDto messagesDto = messagesService.getOneMessage(messages.getId(), sender);

        //then
        assertThat(messagesDto.getTitle()).isEqualTo(messages.getTitle());
        assertThat(messagesDto.getContents()).isEqualTo(messages.getContents());
    }

    @Test
    void deleteMessageTest() {
        //given
        Member sender = memberService.findMember(new MemberLoginDto("a@a.com", "123"));
        Member receiver = memberService.findMember(new MemberLoginDto("c@c.com", "123"));
        MessagesCreateDto messagesCreateDto = new MessagesCreateDto(sender.getUsername(), receiver.getUsername(), "title", "contents");
        Messages messages = messagesService.saveForTest(messagesCreateDto, sender);

        //when
        messagesService.deleteMessage(messages.getId(), sender);
        List<MessagesDto> allMessage = messagesService.getAllMessage(sender);

        //then
        assertThat(allMessage.size()).isEqualTo(0);
        assertThrows(MessageNotExistException.class, () -> {
            messagesService.getOneMessage(messages.getId(), sender);
        });
    }
}