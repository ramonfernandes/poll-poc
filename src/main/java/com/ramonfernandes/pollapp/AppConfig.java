package com.ramonfernandes.pollapp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.ramonfernandes.pollapp.domain.rabbit.RabbitService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.ramonfernandes.pollapp.RabbitConfig.*;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Channel channel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-delayed-type", "direct");

        channel.exchangeDeclare(POLL_EXCHANGE, "x-delayed-message", true, false, args);

        channel.queueDeclare(POLL_CLOSE_QUEUE, true, false, false, new HashMap<>());
        channel.queueBind(POLL_CLOSE_QUEUE, POLL_EXCHANGE, POLL_CLOSE_RK);

        channel.queueDeclare(NOTIFY_RESULT_QUEUE, true, false, false, new HashMap<>());
        channel.queueBind(NOTIFY_RESULT_QUEUE, POLL_EXCHANGE, NOTIFY_RESULT_RK);

        return channel;
    }

}
