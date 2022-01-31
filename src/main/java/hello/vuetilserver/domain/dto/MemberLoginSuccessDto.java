package hello.vuetilserver.domain.dto;

import hello.vuetilserver.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginSuccessDto {

    private String username;
    private String nickname;

    public MemberLoginSuccessDto(Member member) {
        this.username = member.getUsername();
        this.nickname = member.getNickname();
    }
}
