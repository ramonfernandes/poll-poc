package com.ramonfernandes.pollapp.api.vote;

import com.ramonfernandes.pollapp.api.poll.PollResponse;
import com.ramonfernandes.pollapp.domain.vote.VoteEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class VoteMapper {

    @Autowired
    private ModelMapper mapper;

    public VoteEntity toEntity(VoteRequest request) {
        VoteEntity entity = mapper.map(request, VoteEntity.class);

        return entity;
    }

    public VoteResponse toResponse(VoteEntity response) {
        return mapper.map(response, VoteResponse.class);
    }

    public List<VoteResponse> toResponse(Iterable<VoteEntity> entityList) {
        return mapper.map(entityList, new TypeToken<List<PollResponse>>() {
        }.getType());
    }

    public VoteEntity buildEntity(UUID userId, UUID pollId, boolean vote) {
        return VoteEntity.builder()
                .userId(userId)
                .pollId(pollId)
                .is_yes(vote)
                .build();
    }
}
