package com.ramonfernandes.pollapp.domain.poll;

import com.ramonfernandes.pollapp.api.vote.VoteService;
import com.ramonfernandes.pollapp.domain.rabbit.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.ramonfernandes.pollapp.RabbitConfig.POLL_CLOSE_RK;
import static com.ramonfernandes.pollapp.RabbitConfig.POLL_EXCHANGE;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private RabbitService rabbitService;

    @Autowired
    private VoteService voteService;

    public Iterable<PollEntity> findAll() {
        return pollRepository.findAll();
    }

    public PollEntity findById(UUID pollId) throws ChangeSetPersister.NotFoundException {
        return pollRepository.findById(pollId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public PollEntity createPoll(PollEntity pollEntity, Integer secondsToClose) {
        pollRepository.save(pollEntity);
        rabbitService
                .sendMessage(
                        POLL_EXCHANGE,
                        POLL_CLOSE_RK,
                        pollEntity.getPollId().toString().getBytes(StandardCharsets.UTF_8),
                        (secondsToClose * 1000));

        return pollEntity;
    }

    public UUID delete(UUID pollId) {
        pollRepository.deleteById(pollId);
        return pollId;
    }

    public void closePoll(UUID pollId) {
        try {
            PollEntity poll = pollRepository.findById(pollId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);

            poll.set_open(false);

            pollRepository.save(poll);
            voteService.notifyResult(poll);
        } catch (ChangeSetPersister.NotFoundException e) {
            System.out.println("Poll not found");
        }
    }
}
