package hello.vuetilserver.domain;

import hello.vuetilserver.domain.dto.MessagesCreateDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Messages {

    @Id @GeneratedValue
    @Column(name = "messages_id")
    private Long id;

    @Column
    private String title;

    @Lob
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member senderId;

    @Column
    private Long receiverId;

    private LocalDateTime createdAt;

    //==연관관계 메서드==//
    public void setSenderId(Member member) {
        this.senderId = member;
        member.addMessages(this);
    }

    public Messages(MessagesCreateDto messagesCreateDto, Member receiver, Member member) {
        this.title = messagesCreateDto.getTitle();
        this.contents = messagesCreateDto.getContents();
        this.senderId = member;
        this.receiverId = receiver.getId();
        this.createdAt = LocalDateTime.now();
    }
}
