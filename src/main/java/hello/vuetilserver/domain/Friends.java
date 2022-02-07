package hello.vuetilserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Friends {

    @Id
    @GeneratedValue
    @Column(name = "friends_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member friendingMemberId;

    @Column
    private Long friendedMemberId;

    private LocalDateTime friendedTime;

    public void setFriendingMember(Member member) {
        this.friendingMemberId = member;
    }

    public Friends(Member friendingMember, Long friendedMemberId) {
        this.friendingMemberId = friendingMember;
        this.friendedMemberId = friendedMemberId;
        friendedTime = LocalDateTime.now();
    }
}
