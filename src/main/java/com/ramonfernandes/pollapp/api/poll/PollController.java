package com.ramonfernandes.pollapp.api.poll;

import com.ramonfernandes.pollapp.domain.poll.PollService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns a list with all the registered polls"),
            @ApiResponse(code = 500, message = "Something went wrong"),
    })
    @ResponseBody
    public ResponseEntity<Iterable<PollResponse>> getPolls() {
        return ResponseEntity.ok(mapper.toResponse(service.findAll()));
    }


    @GetMapping("/{pollId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns a Poll"),
            @ApiResponse(code = 500, message = "Something went wrong"),
    })
    @ResponseBody
    public ResponseEntity<PollResponse> getPollById(@PathVariable UUID pollId) {
        try {
            return ResponseEntity.ok(mapper.toResponse(service.findById(pollId)));
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Creates a Poll"),
            @ApiResponse(code = 500, message = "Something went wrong"),
    })
    public ResponseEntity<PollResponse> createPoll(@RequestBody PollRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.createPoll(mapper.toEntity(request), request.getSecondsToClose())));
    }


    @GetMapping("/{pollId}/result")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the current state of a Poll"),
            @ApiResponse(code = 500, message = "Something went wrong"),
    })
    @ResponseBody
    public ResponseEntity<PollResultResponse> getResult(@PathVariable UUID pollId) {
        try {
            return ResponseEntity.ok(mapper.toResponse(service.getPollResult(pollId)));
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
