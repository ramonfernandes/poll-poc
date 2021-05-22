package com.ramonfernandes.pollapp.api.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {

    private String title;
    private String name;
    private Integer yes;
    private Integer no;

}
