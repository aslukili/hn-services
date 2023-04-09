package org.loukili.hnpost.service;


import lombok.RequiredArgsConstructor;
import org.loukili.hnpost.dto.CommentRequest;
import org.loukili.hnpost.dto.CommentResponse;
import org.loukili.hnpost.entity.Comment;
import org.loukili.hnpost.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;

    @Override
    public CommentResponse saveComment(CommentRequest commentRequest) {
        // TODO: if commentRequest.parent is not null, then add the comment as a child to the parent
        Comment commentToSave = commentRequest.toComment();

        Comment parentComment = null;
        if (commentRequest.getParent() != null){
            parentComment = commentRepository.findById(commentRequest.getParent()).orElse(null);
            // level of comment = parent level + 1
            assert parentComment != null;
            commentToSave.setLevel(parentComment.getLevel()+1);
        }
        CommentResponse commentResponse = commentRepository.save(commentToSave).toResponse();
        if (parentComment != null) {
            parentComment.getChildren().add(commentResponse.getId());
            commentRepository.save(parentComment);
        }
        return commentResponse;
    }

    @Override
    public List<CommentResponse> getAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(Comment::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> getCommentsOfPost(String postId) {
        List<Comment> comments = commentRepository.findCommentsByPost(postId);
        return comments.stream()
                .map(Comment::toResponse)
                .collect(Collectors.toList());
    }
}
