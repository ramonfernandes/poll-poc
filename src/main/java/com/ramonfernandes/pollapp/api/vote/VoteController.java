package com.ramonfernandes.pollapp.api.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("vote")
public class VoteController {

    @Autowired
    private VoteService service;

    @Autowired
    private VoteMapper mapper;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Iterable<VoteResponse>> getVotes() {
        return ResponseEntity.ok(mapper.toResponse(service.findAll()));
    }

    @GetMapping("/{voteId}")
    @ResponseBody
    public ResponseEntity<VoteResponse> getVoteById(@PathVariable UUID pollId) {
        try {
            return ResponseEntity.ok(mapper.toResponse(service.findById(pollId)));
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<VoteResponse> createVote(@RequestBody VoteRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.save(mapper.toEntity(request))));
    }

    @PostMapping()
    public ResponseEntity<?> vote(
            @RequestParam UUID userId,
            @RequestParam UUID pollId,
            @RequestParam boolean vote) {
        try {
            return ResponseEntity.ok(mapper.toResponse(service.vote(mapper.buildEntity(userId, pollId, vote))));
        } catch (CannotVoteTwiceException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Cannot vote Twice, man");
        }
    }
}
