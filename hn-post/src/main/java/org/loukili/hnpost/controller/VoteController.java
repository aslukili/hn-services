package org.loukili.hnpost.controller;

import lombok.AllArgsConstructor;
import org.loukili.hnpost.dto.VoteRequest;
import org.loukili.hnpost.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/votes")
@AllArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteRequest voteRequest) {
        // TODO: voteRequest does not have the voterUsername by default, i should be able to get it from the Header
        voteService.vote(voteRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
