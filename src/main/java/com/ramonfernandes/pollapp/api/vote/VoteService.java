package com.ramonfernandes.pollapp.api.vote;

import com.ramonfernandes.pollapp.domain.vote.VoteEntity;
import com.ramonfernandes.pollapp.domain.vote.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

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

    public UUID delete(UUID pollId) {
        voteRepository.deleteById(pollId);
        return pollId;
    }

    public VoteEntity vote(VoteEntity entity) throws CannotVoteTwiceException {
        if (!voteRepository.existsByPollIdAndUserId(entity.getPollId(), entity.getUserId()))
            voteRepository.save(entity);
        else
            throw new CannotVoteTwiceException();
        return entity;
    }
}
