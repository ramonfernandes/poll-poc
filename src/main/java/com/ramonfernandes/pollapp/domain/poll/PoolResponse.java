package com.ramonfernandes.pollapp.domain.poll;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PoolResponse {

    private UUID poolId;
    private String title;
    private String description;
    private LocalDate createDate;
    private boolean isOpen;

}
