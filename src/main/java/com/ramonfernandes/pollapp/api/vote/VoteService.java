package com.ramonfernandes.pollapp.api.vote;

import com.ramonfernandes.pollapp.api.CpfValidator;
import com.ramonfernandes.pollapp.api.InvalidCpfException;
import com.ramonfernandes.pollapp.domain.poll.PollEntity;
import com.ramonfernandes.pollapp.domain.poll.PollService;
import com.ramonfernandes.pollapp.domain.rabbit.RabbitService;
import com.ramonfernandes.pollapp.domain.vote.VoteEntity;
import com.ramonfernandes.pollapp.domain.vote.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    public PollService pollService;

    public Iterable<VoteEntity> findAll() {
        return voteRepository.findAll();
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

    public List<VoteEntity> findAllByPollId(UUID pollId) {
        return voteRepository.findAllByPollId(pollId);
    }
}
