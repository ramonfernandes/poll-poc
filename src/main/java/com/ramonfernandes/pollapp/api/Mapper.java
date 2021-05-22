package com.ramonfernandes.pollapp.api;

import com.ramonfernandes.pollapp.domain.poll.PollEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Mapper {

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

    public List<PollResponse> toResponse(Iterable<PollEntity> all) {
        return mapper.map(all, new TypeToken<List<PollResponse>>() {
        }.getType());
    }
}
