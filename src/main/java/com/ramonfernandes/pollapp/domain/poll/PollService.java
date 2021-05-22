package com.ramonfernandes.pollapp.domain.poll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PollService {

    @Autowired
    private PollRepository repository;

    public Iterable<PollEntity> findAll() {
        return repository.findAll();
    }

    public PollEntity findById(UUID pollId) throws ChangeSetPersister.NotFoundException {
        return repository.findById(pollId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public PollEntity save(PollEntity pollEntity) {
        return repository.save(pollEntity);
    }

    public UUID delete(UUID pollId) {
        repository.deleteById(pollId);
        return pollId;
    }
}
