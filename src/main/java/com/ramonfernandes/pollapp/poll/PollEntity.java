package com.ramonfernandes.pollapp.poll;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "poll_table")
public class PollEntity {

    @Id
    @Column(name = "poll_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID pollId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "is_open")
    private boolean is_open;

}
