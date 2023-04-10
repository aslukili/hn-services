package org.loukili.hnpost.service;

import lombok.RequiredArgsConstructor;
import org.loukili.hnpost.dto.VoteRequest;
import org.loukili.hnpost.entity.Submission;
import org.loukili.hnpost.entity.Vote;
import org.loukili.hnpost.exception.AlreadyVotedException;
import org.loukili.hnpost.exception.SubmissionNotFoundException;
import org.loukili.hnpost.feignclient.UserClient;
import org.loukili.hnpost.repository.SubmissionRepository;
import org.loukili.hnpost.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.loukili.hnpost.entity.VoteType.UPVOTE;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService{
    private final SubmissionService submissionService;
    private final VoteRepository voteRepository;
    private final SubmissionRepository submissionRepository;
    private final UserClient userClient;

    @Override
    @Transactional
    public void vote(VoteRequest voteRequest, String authToken) {
        Submission submission = submissionService.findById(voteRequest.getPost())
                .orElseThrow(() -> new SubmissionNotFoundException(voteRequest.getPost()));

        Vote existingVote = voteRepository.findTopByVoterUsernameAndPostOrderByIdDesc(voteRequest.getVoterUsername(), submission.getId())
                .orElse(null);

        if (existingVote != null && existingVote.getVoteType().equals(voteRequest.getVoteType().toString())) {
            throw new AlreadyVotedException("You have already " + voteRequest.getVoteType() + "'d for this post");
        } else if (existingVote != null && !existingVote.getVoteType().equals(voteRequest.getVoteType().toString())) {
            existingVote.setVoteType(voteRequest.getVoteType().toString());
            voteRepository.save(existingVote);
        }

        if (existingVote==null){
            Vote vote = voteRequest.toVote();
            voteRepository.save(vote);
        }

        if (UPVOTE.equals(voteRequest.getVoteType())) {
            submission.setUpVotes(submission.getUpVotes() + 1);
            submission.setScore(submission.getScore() + 1);
            // feign call to update the user's karma
            System.out.println(authToken);
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", authToken);
            userClient.incrementKarma(submission.getAuthorUsername(), headers);
        } else {
            submission.setDownVotes(submission.getDownVotes() + 1);
            submission.setScore(submission.getScore() - 1);
        }
        submissionRepository.save(submission);
    }

}
