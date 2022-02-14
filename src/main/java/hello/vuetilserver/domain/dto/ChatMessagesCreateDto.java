package hello.vuetilserver.domain.dto;

import hello.vuetilserver.domain.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessagesCreateDto {

    private MessageType type;
    private String content;
    private String sender;
}
