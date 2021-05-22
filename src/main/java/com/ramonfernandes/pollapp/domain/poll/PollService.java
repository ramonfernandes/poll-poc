package com.ramonfernandes.pollapp.domain.poll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    public Iterable<PollEntity> findAll() {
        return pollRepository.findAll();
    }

    public PollEntity findById(UUID pollId) throws ChangeSetPersister.NotFoundException {
        return pollRepository.findById(pollId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public PollEntity save(PollEntity pollEntity) {
        return pollRepository.save(pollEntity);
    }

    public UUID delete(UUID pollId) {
        pollRepository.deleteById(pollId);
        return pollId;
    }
}
