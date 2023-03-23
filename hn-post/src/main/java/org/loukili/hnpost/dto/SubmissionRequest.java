package org.loukili.hnpost.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.loukili.hnpost.entity.Submission;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SubmissionRequest {
    private String authorUsername;
    // TODO: the type should be an enum, either a tell, ask...
    private String type;
    private String title;
    private String url;
    private String description;

    public Submission toSubmission() {
        return Submission.builder()
                .authorUsername(this.authorUsername)
                .type(this.type)
                .title(this.title)
                .url(this.url)
                .description(this.description)
                .build();
    }
}
