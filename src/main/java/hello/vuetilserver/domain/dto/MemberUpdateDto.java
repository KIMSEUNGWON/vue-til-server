package hello.vuetilserver.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDto {

    private String username;
    private String currentPassword;
    private String changePassword;
    private String changePasswordCheck;
    private String nickname;

}
