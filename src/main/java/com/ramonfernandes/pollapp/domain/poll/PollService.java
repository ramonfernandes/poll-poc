package com.ramonfernandes.pollapp.domain.poll;

import static com.ramonfernandes.pollapp.RabbitConfig.POLL_CLOSE_RK;
import static com.ramonfernandes.pollapp.RabbitConfig.POLL_EXCHANGE;

import com.ramonfernandes.pollapp.domain.rabbit.RabbitService;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PollService {

  @Autowired
  private PollRepository pollRepository;

  @Autowired
  private RabbitService rabbitService;

  public Iterable<PollEntity> findAll() {
    return pollRepository.findAll();
  }

  public PollEntity findById(UUID pollId) throws ChangeSetPersister.NotFoundException {
    return pollRepository.findById(pollId)
        .orElseThrow(ChangeSetPersister.NotFoundException::new);
  }

  public PollEntity createPoll(PollEntity pollEntity) {
    pollRepository.save(pollEntity);
    rabbitService
        .sendMessage(
            POLL_EXCHANGE,
            POLL_CLOSE_RK,
            pollEntity.getPollId().toString().getBytes(StandardCharsets.UTF_8),
            10000);

    return pollEntity;
  }

  public UUID delete(UUID pollId) {
    pollRepository.deleteById(pollId);
    return pollId;
  }
}
