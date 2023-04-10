package org.loukili.hnpost.service;

import org.loukili.hnpost.dto.VoteRequest;
import org.springframework.stereotype.Service;

@Service
public interface VoteService {
    void vote(VoteRequest voteRequest, String authToken);
}
