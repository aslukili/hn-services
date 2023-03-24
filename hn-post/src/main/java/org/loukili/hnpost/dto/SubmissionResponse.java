package org.loukili.hnpost.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.loukili.hnpost.entity.Comment;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SubmissionResponse {
    private String id;
    private String authorUsername;
    // TODO: the type should be an enum, either a tell, ask...
    private String type;
    private String title;
    private String url;
    private String description;
    private List<String> tags;
    private List<String> comments;
    private int score;
    private int upVotes;
    private int downVotes;
    // TODO: numberOfComments could be removed and calculated...
    private int descendants;
    private boolean isNotDeleted;
    private boolean isNotEdited;
    private Date createdAt;
    private Date updatedAt;
}
