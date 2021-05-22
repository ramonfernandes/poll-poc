package com.ramonfernandes.pollapp;

import com.ramonfernandes.pollapp.api.poll.PollMapper;
import com.ramonfernandes.pollapp.api.user.UserMapper;
import org.modelmapper.ModelMapper;
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

}
