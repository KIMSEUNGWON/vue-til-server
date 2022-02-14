package hello.vuetilserver.domain.dto;

import hello.vuetilserver.domain.ChatRoomsEnter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomsEnterListDto {

    private String username;
    private String nickname;
    private LocalDateTime enteredAt;

    public ChatRoomsEnterListDto(ChatRoomsEnter chatRoomsEnter) {
        this.username = chatRoomsEnter.getMember().getUsername();
        this.nickname = chatRoomsEnter.getMember().getNickname();
        this.enteredAt = chatRoomsEnter.getEnteredAt();
    }
}
