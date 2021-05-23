package com.ramonfernandes.pollapp.domain.vote;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoteRepository extends CrudRepository<VoteEntity, UUID> {
    boolean existsByPollIdAndCpf(UUID pollId, String cpf);

    List<VoteEntity> findAllByPollId(UUID pollId);
}
