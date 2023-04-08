package org.loukili.hnpost.service;


import org.loukili.hnpost.dto.CommentRequest;
import org.loukili.hnpost.dto.CommentResponse;
import org.loukili.hnpost.dto.SubmissionRequest;
import org.loukili.hnpost.dto.SubmissionResponse;
import org.loukili.hnpost.entity.Comment;
import org.loukili.hnpost.entity.Submission;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SubmissionService {
    List<SubmissionResponse> getAll();

    SubmissionResponse saveSubmission(SubmissionRequest submissionRequest);

    SubmissionResponse editSubmission(String postId, SubmissionRequest submissionRequest);

    Optional<Submission> findById(String postId);

    boolean deleteSubmission(String postId);

    CommentResponse addComment(String postId, CommentRequest commentRequest);

    List<SubmissionResponse> getAllByPage(int page, int size);

    List<CommentResponse> getCommentsOfPost(String postId);
}
