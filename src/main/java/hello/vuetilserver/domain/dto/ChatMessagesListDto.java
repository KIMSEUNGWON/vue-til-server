package hello.vuetilserver.domain.dto;

import hello.vuetilserver.domain.ChatMessages;
import hello.vuetilserver.domain.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessagesListDto {

    private MessageType type;
    private String content;
    private String sender;

    public ChatMessagesListDto(ChatMessages chatMessages) {
        this.type = chatMessages.getType();
        this.content = chatMessages.getContent();
        this.sender = chatMessages.getSender();
    }
}
