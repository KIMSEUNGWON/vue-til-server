package hello.vuetilserver.domain;

import hello.vuetilserver.domain.dto.ChatRoomsCreateDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoomsEnter {

    @Id @GeneratedValue
    @Column(name = "chatRoomsEnter_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chatRooms_id")
    private ChatRooms chatRooms;

    private LocalDateTime enteredAt;

    public ChatRoomsEnter(Member member, ChatRooms chatRooms) {
        this.enteredAt = LocalDateTime.now();
//        this.member = setMember(member);
        this.member = member;
        this.chatRooms = setChatRooms(chatRooms);
    }

    //==연관관계 메서드==//
//    public Member setMember(Member member) {
//        member.setChatRoomsEnter(this);
//        return member;
//    }

    public ChatRooms setChatRooms(ChatRooms chatRooms) {
        chatRooms.setChatRoomsEnter(this);
        return chatRooms;
    }
}
