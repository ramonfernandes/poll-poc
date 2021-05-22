package com.ramonfernandes.pollapp.api.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteResponse {
    private UUID pollId;
    private UUID userId;
    private boolean is_yes;
}
