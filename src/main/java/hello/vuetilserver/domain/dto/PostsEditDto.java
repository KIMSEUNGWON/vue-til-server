package hello.vuetilserver.domain.dto;

import hello.vuetilserver.domain.Posts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostsEditDto {

    private String title;
    private String contents;

    public PostsEditDto(Posts posts) {
        this.title = posts.getTitle();
        this.contents = posts.getContents();
    }
}
