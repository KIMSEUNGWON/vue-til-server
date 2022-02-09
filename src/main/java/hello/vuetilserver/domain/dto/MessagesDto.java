package hello.vuetilserver.domain.dto;

import hello.vuetilserver.domain.Messages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagesDto {

    private Long id;
    private String title;
    private String contents;
    private String sender;
    private String receiver;
    private LocalDateTime createdAt;

    public MessagesDto(Messages messages) {
        this.id = messages.getId();
        this.title = messages.getTitle();
        this.contents = messages.getContents();
        this.sender = messages.getSenderId().getUsername();
        this.createdAt = messages.getCreatedAt();
    }
}
