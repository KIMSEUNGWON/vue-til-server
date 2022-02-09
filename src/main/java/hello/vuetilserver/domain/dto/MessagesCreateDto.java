package hello.vuetilserver.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagesCreateDto {

    private String senderUsername;
    private String receiverUsername;
    private String title;
    private String contents;
}
