package org.loukili.hnpost.repository;

import org.loukili.hnpost.entity.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SubmissionRepository extends MongoRepository<Submission, String> {
    List<Submission> findSubmissionsByAuthorUsername(String username);
}
