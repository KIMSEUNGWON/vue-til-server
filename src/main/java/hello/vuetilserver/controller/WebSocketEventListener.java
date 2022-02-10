package hello.vuetilserver.controller;

import hello.vuetilserver.domain.ChatMessages;
import hello.vuetilserver.domain.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        MessageType type = (MessageType) headerAccessor.getSessionAttributes().get("type");
        log.info("type = " + type);
        log.info("type = " + type.getClass());
        if (username != null || type.equals(MessageType.LEAVE)) {
            log.info("User Disconnected : " + username);

            ChatMessages chatMessages = new ChatMessages(MessageType.LEAVE, username);

            messagingTemplate.convertAndSend("/topic/public", chatMessages);
        }
    }
}
