package com.ramonfernandes.pollapp.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.ramonfernandes.pollapp.domain.rabbit.RabbitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RabbitServiceTest {

    private Channel channel;
    private RabbitService rabbitService;

    @BeforeEach
    public void setup() {
        channel = mock(Channel.class);
        rabbitService = new RabbitService(channel);
    }

    @Test
    public void shouldSendMessage() {
        try {
            rabbitService.sendMessage("EXCHANGE", "ROUTING_KEY", "TESTE".getBytes(StandardCharsets.UTF_8), 47);

            verify(channel).basicPublish(eq("EXCHANGE"), eq("ROUTING_KEY"), any(), eq("TESTE".getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            fail();
        }
    }

}
