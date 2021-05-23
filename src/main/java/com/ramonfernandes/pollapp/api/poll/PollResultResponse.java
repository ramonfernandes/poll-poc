package com.ramonfernandes.pollapp.api.poll;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PollResultResponse {

    private UUID pollId;
    private String title;
    private String description;
    private Integer yes;
    private Integer no;

}
