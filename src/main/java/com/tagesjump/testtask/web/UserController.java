package com.tagesjump.testtask.web;

import com.tagesjump.testtask.model.User;
import com.tagesjump.testtask.repository.UserRepository;
import com.tagesjump.testtask.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/{id}")
    public User getById(@PathVariable Integer id) {
        User user = userRepository.getById(id);
        if (user == null) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "User with id is not found");
        }
        return user;
    }

    @PostMapping(value = "/{id}")
    public void update(@PathVariable Integer id, @RequestBody User user) {
        userRepository.update(id, user);
    }
}
