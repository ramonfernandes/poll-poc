package com.ramonfernandes.pollapp.domain.vote;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "voting_table")
@AllArgsConstructor
@NoArgsConstructor
public class VoteEntity {

    @Id
    @Column(name = "vote_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID voteId;

    @Column(name = "poll_id")
    private UUID pollId;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "is_yes")
    private boolean is_yes;

}
