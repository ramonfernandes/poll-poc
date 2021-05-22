package com.ramonfernandes.pollapp.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollRequest {

    private String title;
    private String description;
    private Integer secondsToClose;

}
