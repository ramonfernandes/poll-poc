package com.ramonfernandes.pollapp.api.vote;

import com.ramonfernandes.pollapp.api.InvalidCpfException;
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
    public ResponseEntity<VoteResponse> vote(@RequestBody VoteRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.save(mapper.toEntity(request))));
    }

    @PostMapping()
    public ResponseEntity<?> vote(
            @RequestParam UUID pollId,
            @RequestParam String cpf,
            @RequestParam boolean vote) {
        try {
            return ResponseEntity.ok(mapper.toResponse(service.vote(mapper.buildEntity(pollId, cpf, vote))));
        } catch (InvalidVoteException e) {
            return ResponseEntity.badRequest().body("Your vote is not valid, man. Check if you had already voted or the poll is closed");
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.badRequest().body("We didn't find your poll, man");
        } catch (InvalidCpfException e) {
            return ResponseEntity.badRequest().body("Your cpf is invalid, man");
        }
    }
}
