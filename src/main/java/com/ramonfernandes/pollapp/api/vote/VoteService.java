package com.ramonfernandes.pollapp.api.vote;

import com.ramonfernandes.pollapp.RabbitConfig;
import com.ramonfernandes.pollapp.api.CpfValidator;
import com.ramonfernandes.pollapp.api.InvalidCpfException;
import com.ramonfernandes.pollapp.domain.poll.PollEntity;
import com.ramonfernandes.pollapp.domain.poll.PollService;
import com.ramonfernandes.pollapp.domain.rabbit.PollResultMessageBody;
import com.ramonfernandes.pollapp.domain.rabbit.RabbitService;
import com.ramonfernandes.pollapp.domain.vote.VoteEntity;
import com.ramonfernandes.pollapp.domain.vote.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static com.ramonfernandes.pollapp.RabbitConfig.NOTIFY_RESULT_RK;
import static com.ramonfernandes.pollapp.RabbitConfig.POLL_EXCHANGE;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    public PollService pollService;

    @Autowired
    public RabbitService rabbitService;

    public Iterable<VoteEntity> findAll() {
        return voteRepository.findAll();
    }

    public VoteEntity findById(UUID pollId) throws ChangeSetPersister.NotFoundException {
        return voteRepository.findById(pollId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public VoteEntity save(VoteEntity VoteEntity) {
        return voteRepository.save(VoteEntity);
    }

    public VoteEntity vote(VoteEntity entity) throws InvalidVoteException, ChangeSetPersister.NotFoundException, InvalidCpfException {
        PollEntity pollEntity = pollService.findById(entity.getPollId());
        CpfValidator.validate(entity.getCpf());
        if (!voteRepository.existsByPollIdAndCpf(entity.getPollId(), entity.getCpf()) && pollEntity.is_open())
            save(entity);
        else
            throw new InvalidVoteException();
        return entity;
    }

    public void notifyResult(PollEntity pollEntity) {
        List<VoteEntity> votes = voteRepository.findAllByPollId(pollEntity.getPollId());
        int yesses = (int) votes.stream()
                .filter(VoteEntity::is_yes).count();

        PollResultMessageBody messageBody = PollResultMessageBody.builder()
                .pollId(pollEntity.getPollId())
                .description(pollEntity.getDescription())
                .title(pollEntity.getTitle())
                .yes(yesses)
                .no(votes.size() - yesses)
                .build();

        rabbitService.sendMessage(POLL_EXCHANGE, NOTIFY_RESULT_RK, messageBody.toString().getBytes(StandardCharsets.UTF_8), 0);
    }
}
