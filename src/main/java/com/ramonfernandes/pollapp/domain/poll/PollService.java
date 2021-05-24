package com.ramonfernandes.pollapp.domain.poll;

import com.ramonfernandes.pollapp.api.vote.VoteService;
import com.ramonfernandes.pollapp.domain.rabbit.RabbitService;
import com.ramonfernandes.pollapp.domain.vote.VoteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static com.ramonfernandes.pollapp.RabbitConfig.*;

@Service
public class PollService {

    private PollRepository pollRepository;
    private RabbitService rabbitService;
    private VoteService voteService;

    @Autowired
    public PollService(PollRepository pollRepository, RabbitService rabbitService, VoteService voteService) {
        this.pollRepository = pollRepository;
        this.rabbitService = rabbitService;
        this.voteService = voteService;
    }

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
            notifyResult(poll);
        } catch (ChangeSetPersister.NotFoundException e) {
            System.out.println("Poll not found");
        }
    }

    public void notifyResult(PollEntity pollEntity) {
        PollResult messageBody = getPollResult(pollEntity);

        rabbitService.sendMessage(POLL_EXCHANGE, NOTIFY_RESULT_RK, messageBody.toString().getBytes(StandardCharsets.UTF_8), 0);
    }

    public PollResult getPollResult(UUID pollId) throws ChangeSetPersister.NotFoundException {
        PollEntity entity = findById(pollId);
        return getPollResult(entity);
    }

    private PollResult getPollResult(PollEntity pollEntity) {
        List<VoteEntity> votes = voteService.findAllByPollId(pollEntity.getPollId());
        int yesses = (int) votes.stream()
                .filter(VoteEntity::is_yes).count();

        return PollResult.builder()
                .pollId(pollEntity.getPollId())
                .description(pollEntity.getDescription())
                .title(pollEntity.getTitle())
                .yes(yesses)
                .no(votes.size() - yesses)
                .build();
    }

}
