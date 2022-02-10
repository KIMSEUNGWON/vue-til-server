package hello.vuetilserver.controller;

import hello.vuetilserver.domain.ChatMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
@Slf4j
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessages sendMessage(@Payload ChatMessages chatMessages) {
        return chatMessages;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessages addUser(@Payload ChatMessages chatMessages, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessages.getSender());
        return chatMessages;
    }

    @MessageMapping("/chat.leaveUser")
    @SendTo("/topic/public")
    public void leaveUser(@Payload ChatMessages chatMessages, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("type", chatMessages.getType());
    }
}
