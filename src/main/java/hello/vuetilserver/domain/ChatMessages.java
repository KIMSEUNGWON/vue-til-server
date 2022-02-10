package hello.vuetilserver.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatMessages {

    @Id @GeneratedValue
    @Column(name = "chatMessages_id")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column
    private String content;

    @Column
    private String sender;

    private LocalDateTime createdAt;

    public ChatMessages(MessageType messageType, String sender) {
        this.type = messageType;
        this.sender = sender;
        this.createdAt = LocalDateTime.now();
    }
}
