package com.ramonfernandes.pollapp.domain.poll;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Builder
@Getter
@ToString
public class PollResult {

    private UUID pollId;
    private String title;
    private String description;
    private Integer yes;
    private Integer no;

}
