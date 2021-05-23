package com.ramonfernandes.pollapp.domain.poll;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.ramonfernandes.pollapp.RabbitConfig.POLL_CLOSE_QUEUE;

@Component
public class PollConsumer {

    @Autowired
    public Channel channel;

    @PostConstruct
    public void consumeMessage() throws IOException, TimeoutException {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String mensagem = new String(body, "UTF-8");
                System.out.println("Mensagem recebida: " + mensagem);
            }
        };
        channel.basicConsume(POLL_CLOSE_QUEUE, true, consumer);
    }
}
