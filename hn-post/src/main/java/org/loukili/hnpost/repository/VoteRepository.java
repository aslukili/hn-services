package org.loukili.hnpost.repository;

import org.loukili.hnpost.entity.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {
    Optional<Vote> findTopByVoterUsernameAndPostOrderByIdDesc(String voterUsername, String post);
}
