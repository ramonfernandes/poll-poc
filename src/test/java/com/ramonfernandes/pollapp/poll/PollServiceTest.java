package com.ramonfernandes.pollapp.poll;

import com.ramonfernandes.pollapp.api.vote.VoteService;
import com.ramonfernandes.pollapp.domain.poll.PollEntity;
import com.ramonfernandes.pollapp.domain.poll.PollRepository;
import com.ramonfernandes.pollapp.domain.poll.PollService;
import com.ramonfernandes.pollapp.domain.rabbit.RabbitService;
import org.junit.Before;

import java.nio.charset.StandardCharsets;

import static com.ramonfernandes.pollapp.RabbitConfig.POLL_CLOSE_RK;
import static com.ramonfernandes.pollapp.RabbitConfig.POLL_EXCHANGE;
import static org.mockito.Mockito.mock;

public class PollServiceTest {

    private PollService service;
    private PollRepository pollRepository;
    private RabbitService rabbitService;
    private VoteService voteService;

    @Before
    public void setup() {
        pollRepository = mock(PollRepository.class);
        rabbitService = mock(RabbitService.class);
        voteService = mock(VoteService.class);
        service = new PollService(pollRepository, rabbitService, voteService);
    }

    @Before
    public void shouldCreateAPoll() {
        service.createPoll()
    }

    public PollEntity createPoll(PollEntity pollEntity, Integer secondsToClose) {
        pollRepository.save(pollEntity);
        rabbitService
                .sendMessage(
                        POLL_EXCHANGE,
                        POLL_CLOSE_RK,
                        pollEntity.getPollId().toString().getBytes(StandardCharsets.UTF_8),
                        (secondsToClose * 1000));

        return pollEntity;
    }

}
