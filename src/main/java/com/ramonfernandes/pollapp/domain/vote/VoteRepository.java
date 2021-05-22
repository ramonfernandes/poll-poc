package com.ramonfernandes.pollapp.domain.vote;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteRepository extends CrudRepository<VoteEntity, UUID> {

    boolean existsByPollIdAndUserId(UUID userId, UUID pollId);

}
