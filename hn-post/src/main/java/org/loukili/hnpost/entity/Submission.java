package org.loukili.hnpost.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.loukili.hnpost.dto.SubmissionResponse;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Document(value = "hn-post")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Submission {
    @Id
    private String id;
    private String authorUsername;
    // TODO: the type should be an enum, either a tell, ask...
    private String type;
    private String title;
    private String url;
    private String description;
    private List<String> tags;
    private List<String> comments = new ArrayList<>();
    private int score = 0;
    private int upVotes = 0;
    private int downVotes = 0;
    // TODO: descendants is the number of comments with post's id, and it could be removed and calculated...
    private int descendants = 0;
    private boolean isNotDeleted = true;
    private boolean isNotEdited = true;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;


    // TODO: add comments to response
    public SubmissionResponse toResponse() {
        return SubmissionResponse.builder()
                .id(this.id)
                .authorUsername(this.authorUsername)
                .type(this.type)
                .title(this.title)
                .url(this.url)
                .description(this.description)
                .tags(this.tags)
                .comments(this.comments)
                .score(this.score)
                .upVotes(this.upVotes)
                .downVotes(this.downVotes)
                .descendants(this.descendants)
                .isNotDeleted(this.isNotDeleted)
                .isNotEdited(this.isNotEdited)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

}
