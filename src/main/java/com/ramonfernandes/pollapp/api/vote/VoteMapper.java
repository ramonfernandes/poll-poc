package com.ramonfernandes.pollapp.api.vote;

import com.ramonfernandes.pollapp.api.poll.PollResponse;
import com.ramonfernandes.pollapp.domain.vote.VoteEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class VoteMapper {

    private ModelMapper mapper;

    @Autowired
    public VoteMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<VoteResponse> toResponse(Iterable<VoteEntity> entityList) {
        return mapper.map(entityList, new TypeToken<List<PollResponse>>() {
        }.getType());
    }

    public VoteEntity buildEntity(UUID pollId, String cpf, boolean vote) {
        return VoteEntity.builder()
                .pollId(pollId)
                .cpf(cpf)
                .is_yes(vote)
                .build();
    }

    public VoteResponse toResponse(VoteEntity vote) {
        return mapper.map(vote, VoteResponse.class);
    }
}
