package com.ramonfernandes.pollapp.domain.vote;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "voting_table")
public class VoteEntity {

    @Id
    @Column(name = "vote_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID voteId;

    @Column(name = "poll_id")
    private UUID pollId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "is_yes")
    private boolean is_yes;

}
