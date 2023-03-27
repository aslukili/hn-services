package org.loukili.hnpost.controller;

import lombok.RequiredArgsConstructor;
import org.loukili.hnpost.dto.CommentResponse;
import org.loukili.hnpost.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/hn-post/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getAllComments() {
        return commentService.getAll();
    }
}
