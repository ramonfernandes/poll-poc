package com.ramonfernandes.pollapp.api.poll;

import com.ramonfernandes.pollapp.domain.poll.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("poll")
public class PollController {

    @Autowired
    private PollService service;

    @Autowired
    private PollMapper mapper;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Iterable<PollResponse>> getPolls() {
        return ResponseEntity.ok(mapper.toResponse(service.findAll()));
    }

    @GetMapping("/{pollId}")
    @ResponseBody
    public ResponseEntity<PollResponse> getPollById(@PathVariable UUID pollId) {
        try {
            return ResponseEntity.ok(mapper.toResponse(service.findById(pollId)));
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<PollResponse> createPoll(@RequestBody PollRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.createPoll(mapper.toEntity(request), request.getSecondsToClose())));
    }

    @DeleteMapping("/delete/{pollId}")
    public ResponseEntity<UUID> deletePoll(@PathVariable UUID pollId) {
        try {
            return ResponseEntity.ok(service.delete(pollId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
