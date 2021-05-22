package com.ramonfernandes.pollapp.api.user;

import com.ramonfernandes.pollapp.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper mapper;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Iterable<UserResponse>> getUsers() {
        return ResponseEntity.ok(mapper.toResponse(service.findAll()));
    }

    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok(mapper.toResponse(service.findById(userId)));
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.save(mapper.toEntity(request))));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<UUID> deleteUser(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok(service.delete(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
