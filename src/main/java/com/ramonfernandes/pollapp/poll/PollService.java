package com.ramonfernandes.pollapp.poll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PollService {

    @Autowired
    private PollRepository repository;

    public Iterable<PollEntity> findAll() {
        return repository.findAll();
    }
}
