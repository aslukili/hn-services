package org.loukili.hnpost.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.loukili.hnpost.entity.Comment;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommentRequest {
    private String authorUsername;
    private String content;
    private String post;
    private String parent; // parent is null if the comment is a top level comment
    public Comment toComment() {
        return Comment.builder()
                .authorUsername(this.authorUsername)
                .content(this.content)
                .post(this.post)
                .parent(this.parent)
                .build();
    }
}
