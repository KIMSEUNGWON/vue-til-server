package hello.vuetilserver.domain.dto;

import hello.vuetilserver.domain.FriendsSearchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendsSearchDto {

    private FriendsSearchType type;
    private String value;
}
