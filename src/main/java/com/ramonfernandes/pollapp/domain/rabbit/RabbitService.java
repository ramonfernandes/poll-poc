package com.ramonfernandes.pollapp.domain.rabbit;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.HashMap;

import com.ramonfernandes.pollapp.domain.poll.PollConsumer;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitService {

    @Autowired
    public Channel channel;

    public void sendMessage(String exchange, String routingKey, byte[] body, Integer delay) {
        try {
            HashMap<String, Object> headers = new HashMap<>();
            headers.put("x-delay", delay);

            BasicProperties build = new BasicProperties()
                    .builder()
                    .headers(headers)
                    .build();

            channel.basicPublish(exchange, routingKey, build, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
