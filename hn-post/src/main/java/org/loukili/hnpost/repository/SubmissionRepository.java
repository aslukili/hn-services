package org.loukili.hnpost.repository;

import org.loukili.hnpost.entity.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubmissionRepository extends MongoRepository<Submission, String> {
}
