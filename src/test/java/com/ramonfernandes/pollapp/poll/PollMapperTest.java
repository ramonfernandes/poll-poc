package com.ramonfernandes.pollapp.poll;

import com.ramonfernandes.pollapp.api.poll.PollMapper;
import com.ramonfernandes.pollapp.api.poll.PollRequest;
import com.ramonfernandes.pollapp.domain.poll.PollEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

public class PollMapperTest {


    private ModelMapper modelMapper;

    private PollMapper pollMapper;

    @BeforeEach
    public void initMocks() {
        modelMapper = Mockito.mock(ModelMapper.class);
        pollMapper = new PollMapper(modelMapper);
    }

    @Test
    public void shouldMapRequestToEntity() {

        String description = "Test Description";
        String title = "Test Title";

        PollRequest pollRequest = new PollRequest();
        pollRequest.setDescription(description);
        pollRequest.setTitle(title);
        pollRequest.setSecondsToClose(60);

        PollEntity entity = new PollEntity();
        entity.set_open(false);
        entity.setDescription(description);
        entity.setTitle(title);

        Mockito.when(modelMapper.map(pollRequest, PollEntity.class)).thenReturn(entity);
        PollEntity entityResponse = pollMapper.toEntity(pollRequest);

        Assertions.assertEquals(description, entityResponse.getDescription());
        Assertions.assertEquals(title, entityResponse.getTitle());
        Assertions.assertTrue(entity.is_open());
    }


}
