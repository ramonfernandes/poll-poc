package com.ramonfernandes.pollapp.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity save(UserEntity entity) {
        return userRepository.save(entity);
    }

    public UserEntity findById(UUID userId) throws ChangeSetPersister.NotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public UUID delete(UUID userId) {
        userRepository.deleteById(userId);

        return userId;
    }
}
