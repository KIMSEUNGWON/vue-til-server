package hello.vuetilserver.domain;

import hello.vuetilserver.domain.dto.PostsEditDto;
import hello.vuetilserver.domain.dto.PostsSaveDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts {

    @Id @GeneratedValue
    @Column(name = "posts_id")
    private Long id;

    @Column(unique = true)
    private String title;

    @Lob
    @Column(length = 200)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member createdBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Posts(PostsSaveDto postsSaveDto, Member member) {
        this.title = postsSaveDto.getTitle();
        this.contents = postsSaveDto.getContents();
        this.createdBy = member;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTitleAndContents(PostsEditDto postsEditDto) {
        this.title = postsEditDto.getTitle();
        this.contents = postsEditDto.getContents();
    }
}
