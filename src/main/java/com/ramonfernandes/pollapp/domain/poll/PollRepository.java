package com.ramonfernandes.pollapp.domain.poll;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PollRepository extends CrudRepository<PollEntity, UUID> {
}
