package com.ramonfernandes.pollapp.poll;

import com.ramonfernandes.pollapp.api.vote.VoteService;
import com.ramonfernandes.pollapp.domain.poll.PollEntity;
import com.ramonfernandes.pollapp.domain.poll.PollRepository;
import com.ramonfernandes.pollapp.domain.poll.PollResult;
import com.ramonfernandes.pollapp.domain.poll.PollService;
import com.ramonfernandes.pollapp.domain.rabbit.RabbitService;
import com.ramonfernandes.pollapp.domain.vote.VoteEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.ramonfernandes.pollapp.RabbitConfig.*;
import static org.mockito.Mockito.*;

public class PollServiceTest {

    private PollService service;
    private PollRepository pollRepository;
    private RabbitService rabbitService;
    private VoteService voteService;

    @BeforeEach
    public void setup() {
        pollRepository = mock(PollRepository.class);
        rabbitService = mock(RabbitService.class);
        voteService = mock(VoteService.class);
        service = new PollService(pollRepository, rabbitService, voteService);
    }

    @Test
    public void shouldCreateAPoll() {
        PollEntity entity = new PollEntity();
        entity.setPollId(UUID.randomUUID());
        when(pollRepository.save(entity)).thenReturn(entity);

        PollEntity pollEntity = service.createPoll(entity, 60);
        verify(rabbitService)
                .sendMessage(POLL_EXCHANGE,
                        POLL_CLOSE_RK,
                        entity.getPollId().toString().getBytes(StandardCharsets.UTF_8),
                        60000);

        Assertions.assertEquals(entity, pollEntity);
    }

    @Test
    public void shouldCloseTheQueue() {
        PollEntity entity = buildEntity(UUID.randomUUID());

        when(pollRepository.findById(eq(entity.getPollId())))
                .thenReturn(Optional.of(entity));
        when(voteService.findAllByPollId(eq(entity.getPollId())))
                .thenReturn(Collections.singletonList(new VoteEntity(UUID.randomUUID(), entity.getPollId(), "01234567890", true)));


        service.closePoll(entity.getPollId());
        verify(pollRepository).save(entity);
        verify(rabbitService).sendMessage(eq(POLL_EXCHANGE), eq(NOTIFY_RESULT_RK), eq(getBytes(entity)), eq(0));
    }

    private byte[] getBytes(PollEntity entity) {
        return PollResult.builder()
                .pollId(entity.getPollId())
                .description(entity.getDescription())
                .title(entity.getTitle())
                .yes(1)
                .no(0)
                .build().toString().getBytes(StandardCharsets.UTF_8);
    }

    private PollEntity buildEntity(UUID randomUUID) {
        PollEntity entity = new PollEntity();
        entity.setPollId(randomUUID);
        entity.set_open(true);
        entity.setDescription("Description");
        entity.setTitle("Title");

        return entity;
    }

}
