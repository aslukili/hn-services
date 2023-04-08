package org.loukili.hnpost.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommentResponse {
    private String id;
    private String authorUsername;
    private String content;
    private String post;
    private String parent; // parent is null if the comment is a top level comment
    private List<String> children;
    private int level;
    private int score;
    private int upVotes;
    private int downVotes;
    private boolean isNotDeleted;
    private boolean isNotUpdated;
}
