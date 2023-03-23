package org.loukili.hnpost.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "hn-vote")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Vote {
    @Id
    private String id;
    private String voteType; // voteType should be an enum, either upvote or downvote
    private String voterUsername;
    private String post;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
}
