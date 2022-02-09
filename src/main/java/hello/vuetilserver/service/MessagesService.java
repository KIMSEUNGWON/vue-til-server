package hello.vuetilserver.service;

import hello.vuetilserver.controller.exception.MemberNotExistException;
import hello.vuetilserver.controller.exception.MessageNotExistException;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.Messages;
import hello.vuetilserver.domain.dto.MessagesCreateDto;
import hello.vuetilserver.domain.dto.MessagesDto;
import hello.vuetilserver.repository.MemberRepository;
import hello.vuetilserver.repository.MessagesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessagesService {

    private final MessagesRepository messagesRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MessagesDto save(MessagesCreateDto messagesCreateDto, Member member) {
        log.info("쪽지 생성 메서드");

        Member receiver = memberRepository.findMemberByUsername(messagesCreateDto.getReceiverUsername()).orElseThrow(() -> new MemberNotExistException("존재하지 않는 멤버입니다."));

        Messages saveMessages = messagesRepository.save(new Messages(messagesCreateDto, receiver, member));
        MessagesDto messagesDto = new MessagesDto(saveMessages);

        return messagesDto;
    }

    @Transactional
    public Messages saveForTest(MessagesCreateDto messagesCreateDto, Member member) {
        log.info("쪽지 생성 메서드");

        Member receiver = memberRepository.findMemberByUsername(messagesCreateDto.getReceiverUsername()).orElseThrow(() -> new MemberNotExistException("존재하지 않는 멤버입니다."));

        Messages saveMessages = messagesRepository.save(new Messages(messagesCreateDto, receiver, member));

        return saveMessages;
    }

    public List<MessagesDto> getAllMessage(Member member) {
        log.info("모든 쪽지 조회 메서드");
        List<Messages> messagesList = messagesRepository.findAll(member).orElse(null);

        List<MessagesDto> messagesDtoList = messagesList.stream()
                .map(m -> {
                    Member receiver = memberRepository.getById(m.getReceiverId());
                    return new MessagesDto(m.getId(), m.getTitle(), m.getContents(), member.getUsername(), receiver.getUsername(), m.getCreatedAt());
                })
                .collect(Collectors.toList());

        return messagesDtoList;
    }

    public List<MessagesDto> getAllReceivedMessage(Member member) {
        log.info("모든 받은 쪽지 조회 메서드");
        List<Messages> messagesList = messagesRepository.findAll(member.getId()).orElse(null);

        List<MessagesDto> messagesDtoList = messagesList.stream()
                .map(m -> {
                    Member sender = memberRepository.getById(m.getSenderId().getId());
                    return new MessagesDto(m.getId(), m.getTitle(), m.getContents(), sender.getUsername(), member.getUsername(), m.getCreatedAt());
                })
                .collect(Collectors.toList());

        return messagesDtoList;
    }

    public MessagesDto getOneMessage(Long messageId, Member member) {
        log.info("특정 쪽지 조회 메서드");

        Messages messages = messagesRepository.findById(messageId).orElseThrow(() -> new MessageNotExistException("존재하지 않는 쪽지입니다."));
        Member receiver = memberRepository.getById(messages.getReceiverId());
        MessagesDto messagesDto = new MessagesDto(messages.getId(), messages.getTitle(), messages.getContents(), member.getNickname(), receiver.getUsername(), messages.getCreatedAt());

        return messagesDto;
    }

    @Transactional
    public String deleteMessage(Long messageId, Member member) {
        log.info("쪽지 삭제 메서드");

        Messages messages = messagesRepository.findById(messageId).orElseThrow(() -> new MessageNotExistException("존재하지 않는 쪽지입니다."));
        if (messages.getSenderId() != member) {
            log.info("작성자와 삭제자가 일치하지 않습니다");
            return "error";
        }

        messagesRepository.delete(messages);
        return "success";
    }
}
