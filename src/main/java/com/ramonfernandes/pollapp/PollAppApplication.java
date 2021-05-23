package com.ramonfernandes.pollapp;

import com.ramonfernandes.pollapp.domain.poll.PollConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PollAppApplication {

    @Autowired
    public PollConsumer pollConsumer;

    public static void main(String[] args) {
        SpringApplication.run(PollAppApplication.class, args);
    }

}
