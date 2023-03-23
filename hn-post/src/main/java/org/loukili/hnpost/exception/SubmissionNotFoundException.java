package org.loukili.hnpost.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubmissionNotFoundException extends RuntimeException{
    public SubmissionNotFoundException(String postId) {
        super("Submission not found with postId " + postId);
    }
}
