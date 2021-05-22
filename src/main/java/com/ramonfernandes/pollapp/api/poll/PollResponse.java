package com.ramonfernandes.pollapp.api.poll;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollResponse {
    private UUID pollId;
    private String title;
    private String description;
    private boolean is_open;
}
