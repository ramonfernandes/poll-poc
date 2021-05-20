package com.ramonfernandes.pollapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("poll")
public class PollController {

    @GetMapping
    @ResponseBody
    public List<PoolResponse> getPolls() {

        PoolResponse poolResponse = new PoolResponse();
        return Arrays.asList(poolResponse);

    }

}
