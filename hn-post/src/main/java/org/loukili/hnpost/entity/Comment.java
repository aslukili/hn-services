package org.loukili.hnpost.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.loukili.hnpost.dto.CommentResponse;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(value = "hn-comment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Comment {
    @Id
    private String id;
    private String authorUsername;
    // TODO: the type should be an enum, either a tell, ask...
    private String content;
    private String post;
    private String parent; // parent is null if the comment is a top level comment
    private List<String> children = new ArrayList<>();
    private int level = 0; // level = 0 if parent is null (first level comment) otherwise, level = parent level + 1
    private int score;
    private int upVotes;
    private int downVotes;
    private boolean isNotDeleted = true;
    private boolean isNotUpdated = true;

    public CommentResponse toResponse() {
        return CommentResponse.builder()
                .id(this.id)
                .authorUsername(this.authorUsername)
                .content(this.content)
                .post(this.post)
                .parent(this.parent)
                .children(this.children)
                .level(this.level)
                .score(this.score)
                .upVotes(this.upVotes)
                .downVotes(this.downVotes)
                .isNotDeleted(this.isNotDeleted)
                .isNotUpdated(this.isNotUpdated)
                .build();
    }
}
