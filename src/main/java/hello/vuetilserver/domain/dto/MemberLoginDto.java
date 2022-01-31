package hello.vuetilserver.domain.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginDto {

    private String username;
    private Long password;
}
