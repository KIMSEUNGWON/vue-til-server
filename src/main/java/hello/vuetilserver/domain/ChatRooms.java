package hello.vuetilserver.domain;

import hello.vuetilserver.domain.dto.ChatRoomsCreateDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRooms {

    @Id @GeneratedValue
    @Column(name = "chatRooms_id")
    private Long id;

    @Column
    private String title;

    @OneToMany(mappedBy = "chatRooms", cascade = ALL)
    private List<ChatMessages> chatMessages = new ArrayList<>();

    @OneToMany(mappedBy = "chatRooms", cascade = CascadeType.ALL)
    private List<ChatRoomsEnter> chatRoomsEnterList = new ArrayList<>();

    private LocalDateTime lastChattedAt;

    public ChatRooms(ChatRoomsCreateDto chatRoomsCreateDto) {
        this.title = chatRoomsCreateDto.getTitle();
    }

    //==연관관계 메서드==//
    public void setChatRoomsEnter(ChatRoomsEnter chatRoomsEnter) {
        this.chatRoomsEnterList.add(chatRoomsEnter);
    }

    public void setChatMessages(ChatMessages chatMessages) {
        this.chatMessages.add(chatMessages);
        chatMessages.setChatRooms(this);
        this.lastChattedAt = LocalDateTime.now();
    }
}
