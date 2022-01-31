package hello.vuetilserver.domain;

import hello.vuetilserver.domain.dto.MemberDto;
import hello.vuetilserver.domain.dto.MemberLoginDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private Long password;

    @Column(name = "nickname")
    private String nickname;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "createdBy")
    private List<Posts> postsList = new ArrayList<>();

    @Column(unique = true)
    private String token;

    public Member(MemberDto memberDto) {
        this.username = memberDto.getUsername();
        this.password = memberDto.getPassword();
        this.nickname = memberDto.getNickname();
        this.token = UUID.randomUUID().toString();
    }

    public Member(MemberLoginDto memberLoginDto) {
        this.username = memberLoginDto.getUsername();
        this.password = memberLoginDto.getPassword();
    }
}
