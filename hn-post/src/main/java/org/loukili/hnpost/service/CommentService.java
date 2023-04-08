package org.loukili.hnpost.service;

import org.loukili.hnpost.dto.CommentRequest;
import org.loukili.hnpost.dto.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentResponse saveComment(CommentRequest commentRequest);

    List<CommentResponse> getAll();

    List<CommentResponse> getCommentsOfPost(String postId);
}
