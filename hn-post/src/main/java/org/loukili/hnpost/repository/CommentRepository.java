package org.loukili.hnpost.repository;

import org.loukili.hnpost.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findCommentsByPost(String post);
}
