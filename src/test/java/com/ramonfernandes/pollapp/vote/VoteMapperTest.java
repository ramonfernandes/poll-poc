package com.ramonfernandes.pollapp.vote;

import com.ramonfernandes.pollapp.api.vote.VoteMapper;
import com.ramonfernandes.pollapp.domain.vote.VoteEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.UUID;

public class VoteMapperTest {

    private ModelMapper modelMapper;

    private VoteMapper voteMapper;

    @Before
    public void setup() {
        modelMapper = Mockito.mock(ModelMapper.class);
        voteMapper = new VoteMapper(modelMapper);
    }

    @Test
    public void shouldBuildEntity() {
        UUID id = UUID.randomUUID();
        String cpf = "01234567890";

        VoteEntity entity = voteMapper.buildEntity(id, cpf, true);

        Assertions.assertEquals(id, entity.getPollId());
        Assertions.assertEquals(cpf, entity.getCpf());
        Assertions.assertTrue(entity.is_yes());
    }


}
