package hello.vuetilserver.domain.dto;

import hello.vuetilserver.domain.Posts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsSaveResultDto {

    private Long _id;
    private String title;
    private String contents;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostsSaveResultDto(Posts posts) {
        this._id = posts.getId();
        this.title = posts.getTitle();
        this.contents = posts.getContents();
        this.createdBy = posts.getCreatedBy().getUsername();
        this.createdAt = posts.getCreatedAt();
        this.updatedAt = posts.getUpdatedAt();
    }
}
