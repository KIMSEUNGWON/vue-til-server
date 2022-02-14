package hello.vuetilserver.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatMessages")
    private ChatRooms chatRooms;

    public ChatMessages(MessageType messageType, String sender) {
        this.type = messageType;
        this.sender = sender;
        this.createdAt = LocalDateTime.now();
    }

    //==연관관계 메서드==//
    public void setChatRooms(ChatRooms chatRooms) {
        this.chatRooms = chatRooms;
    }
}
