package org.loukili.hnpost.controller;

import lombok.RequiredArgsConstructor;
import org.loukili.hnpost.dto.CommentRequest;
import org.loukili.hnpost.dto.CommentResponse;
import org.loukili.hnpost.dto.SubmissionRequest;
import org.loukili.hnpost.dto.SubmissionResponse;
import org.loukili.hnpost.entity.Comment;
import org.loukili.hnpost.entity.Submission;
import org.loukili.hnpost.exception.SubmissionNotFoundException;
import org.loukili.hnpost.service.SubmissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/hn-post/submissions")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;
    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<SubmissionResponse> getAllSubmissions(){
        return submissionService.getAll();
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<SubmissionResponse> getAllSubmissionsByPage(@RequestParam int page, @RequestParam int size){
        return submissionService.getAllByPage(page, size);
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public SubmissionResponse saveSubmission(@RequestBody SubmissionRequest submissionRequest){
        return submissionService.saveSubmission(submissionRequest);
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public SubmissionResponse findSubmission(@PathVariable String postId){
        Submission submission = submissionService.findById(postId)
                .orElseThrow(() -> new SubmissionNotFoundException(postId));

        return submission.toResponse();
    }

    // get submissions of a particular user
    @GetMapping("/user/{username}")
    @ResponseStatus(HttpStatus.OK)
    public List<SubmissionResponse> getSubmissionsOfUser(@PathVariable String username){
        return submissionService.findByAuthor(username);
    }

    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public SubmissionResponse editSubmission(@PathVariable String postId, @RequestBody SubmissionRequest submissionRequest){
        return submissionService.editSubmission(postId, submissionRequest);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteSubmission(@PathVariable String postId) {
        return submissionService.deleteSubmission(postId);
    }



    @GetMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getCommentsOfPost(@PathVariable String postId){
        // TODO: implement the method
        return submissionService.getCommentsOfPost(postId);
    }

    @PostMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse saveComment(@PathVariable String postId, @RequestBody CommentRequest commentRequest){
        return submissionService.addComment(postId, commentRequest);
    }

    // TODO: update comment (probably this will be under CommentController class)
    // TODO: delete comment (probably this will be under CommentController class)

}
