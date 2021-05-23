package com.ramonfernandes.pollapp.domain.rabbit;

import lombok.Builder;
import lombok.ToString;

import java.util.UUID;

@Builder
@ToString
public class PollResultMessageBody {

    private UUID pollId;
    private String title;
    private String description;
    private Integer yes;
    private Integer no;

}
