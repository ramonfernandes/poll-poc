package com.ramonfernandes.pollapp;

import static com.ramonfernandes.pollapp.RabbitConfig.POLL_CLOSE_QUEUE;
import static com.ramonfernandes.pollapp.RabbitConfig.POLL_CLOSE_RK;
import static com.ramonfernandes.pollapp.RabbitConfig.POLL_EXCHANGE;

import com.rabbitmq.client.DeliverCallback;
import com.ramonfernandes.pollapp.api.poll.PollMapper;
import com.ramonfernandes.pollapp.api.user.UserMapper;
import com.ramonfernandes.pollapp.api.vote.VoteMapper;
import com.ramonfernandes.pollapp.domain.rabbit.RabbitService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.modelmapper.ModelMapper;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PollMapper pollMapper() {
        return new PollMapper();
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public VoteMapper voteMapper() {
        return new VoteMapper();
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

        return channel;
    }

    @Bean
    public RabbitService rabbitService() {
        return new RabbitService();
    }

}
