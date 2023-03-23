package org.loukili.hnpost.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.loukili.hnpost.entity.Vote;
import org.loukili.hnpost.entity.VoteType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequest {
    private VoteType voteType;
    private String voterUsername;
    private String post;

    public Vote toVote() {
        return Vote.builder()
                .voteType(voteType.name())
                .voterUsername(voterUsername)
                .post(post)
                .build();
    }
}
