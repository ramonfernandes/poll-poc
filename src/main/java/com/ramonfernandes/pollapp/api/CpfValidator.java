package com.ramonfernandes.pollapp.api;

import com.ramonfernandes.pollapp.AbleToVote;
import com.ramonfernandes.pollapp.Status;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class CpfValidator {

    public static void validate(String cpf) throws InvalidCpfException {
        // TODO: falar com o time que esse validador ta esquisito
        final String uri = String.format("https://user-info.herokuapp.com/users/%s", cpf);

        RestTemplate restTemplate = new RestTemplate();
        AbleToVote result = restTemplate.getForObject(uri, AbleToVote.class);

        if (Status.UNABLE_TO_VOTE.equals(Objects.requireNonNull(result).getStatus())) {
            throw new InvalidCpfException();
        }
    }

}
