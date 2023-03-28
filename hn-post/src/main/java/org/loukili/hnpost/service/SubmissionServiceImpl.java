package org.loukili.hnpost.service;

import lombok.RequiredArgsConstructor;
import org.loukili.hnpost.dto.CommentRequest;
import org.loukili.hnpost.dto.CommentResponse;
import org.loukili.hnpost.dto.SubmissionRequest;
import org.loukili.hnpost.dto.SubmissionResponse;
import org.loukili.hnpost.entity.Submission;
import org.loukili.hnpost.exception.SubmissionNotFoundException;
import org.loukili.hnpost.repository.SubmissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService{
    private final SubmissionRepository submissionRepository;
    private final CommentService commentService;
    @Override
    public List<SubmissionResponse> getAll() {
        List<Submission> submissions = submissionRepository.findAll();
        return submissions.stream()
                .map(Submission::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SubmissionResponse saveSubmission(SubmissionRequest submissionRequest) {
        return submissionRepository.save(submissionRequest.toSubmission()).toResponse();
    }

    @Override
    public SubmissionResponse editSubmission(String postId, SubmissionRequest submissionRequest) {
        Submission submission = submissionRepository.findById(postId)
                .orElseThrow(() -> new SubmissionNotFoundException(postId));

        submission.setTitle(submissionRequest.getTitle());
        submission.setUrl(submissionRequest.getUrl());
        submission.setDescription(submissionRequest.getDescription());
        submission.setNotEdited(false);
        return submissionRepository.save(submission).toResponse();
    }

    @Override
    public Optional<Submission> findById(String postId) {
        return submissionRepository.findById(postId);
    }

    @Override
    public boolean deleteSubmission(String postId) {
        Submission submission = submissionRepository.findById(postId)
                .orElseThrow(() -> new SubmissionNotFoundException(postId));

        submission.setNotDeleted(false);
        return true;
    }

    @Override
    public CommentResponse addComment(String postId, CommentRequest commentRequest) {

        // get the submission by id and save the comment
        Submission submission = submissionRepository.findById(postId).orElseThrow(() -> new SubmissionNotFoundException(postId));
        commentRequest.setPost(postId);
        CommentResponse commentResponse = commentService.saveComment(commentRequest);

        // update the submission with the new comment and increment the score and descendents of post by 1
        List<String> comments = submission.getComments();
        comments.add(commentResponse.getId());
        submission.setComments(comments);
        submission.setScore(submission.getScore() + 1);
        submission.setDescendants(submission.getDescendants() + 1);
        submissionRepository.save(submission);
        return commentResponse;
    }

    @Override
    public List<SubmissionResponse> getAllByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Submission> submissions = submissionRepository.findAll(pageable);
        return submissions.stream()
                .map(Submission::toResponse)
                .collect(Collectors.toList());
    }

}
