package com.ramonfernandes.pollapp.api.poll;

import com.ramonfernandes.pollapp.domain.poll.PollEntity;
import com.ramonfernandes.pollapp.domain.poll.PollResult;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PollMapper {

    @Autowired
    private ModelMapper mapper;

    public PollEntity toEntity(PollRequest request) {
        PollEntity entity = mapper.map(request, PollEntity.class);
        entity.set_open(true);

        return entity;
    }

    public PollResponse toResponse(PollEntity response) {
        return mapper.map(response, PollResponse.class);
    }

    public List<PollResponse> toResponse(Iterable<PollEntity> entityList) {
        return mapper.map(entityList, new TypeToken<List<PollResponse>>() {
        }.getType());
    }

    public PollResultResponse toResponse(PollResult pollResult) {
        return mapper.map(pollResult, PollResultResponse.class);
    }
}
