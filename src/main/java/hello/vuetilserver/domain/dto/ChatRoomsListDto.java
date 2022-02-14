package hello.vuetilserver.domain.dto;

import hello.vuetilserver.domain.ChatRooms;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomsListDto {

    private Long id;
    private String title;
    private List<ChatRoomsEnterListDto> chatRoomsEnterListDto;
    private LocalDateTime lastChattedAd;

    public ChatRoomsListDto(ChatRooms chatRooms) {
        this.id = chatRooms.getId();
        this.title = chatRooms.getTitle();
        this.chatRoomsEnterListDto = chatRooms.getChatRoomsEnterList().stream()
                .map(chatRoomsEnter -> new ChatRoomsEnterListDto(chatRoomsEnter))
                .collect(Collectors.toList());
        this.lastChattedAd = chatRooms.getLastChattedAt();
    }

    public ChatRoomsListDto(ChatRooms chatRooms, List<ChatRoomsEnterListDto> chatRoomsEnterListDto) {
        this.title = chatRooms.getTitle();
        this.chatRoomsEnterListDto = new ArrayList<>(chatRoomsEnterListDto);
        this.lastChattedAd = chatRooms.getLastChattedAt();
    }
}
