package com.ramonfernandes.pollapp.api.user;

import com.ramonfernandes.pollapp.domain.user.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMapper {

    @Autowired
    private ModelMapper mapper;

    public UserEntity toEntity(UserRequest userRequest) {
        return mapper.map(userRequest, UserEntity.class);
    }

    public UserResponse toResponse(UserEntity entity) {
        return mapper.map(entity, UserResponse.class);
    }

    public Iterable<UserResponse> toResponse(Iterable<UserEntity> entityList) {
        return mapper.map(entityList, new TypeToken<Iterable<UserResponse>>() {
        }.getType());
    }
}
