package com.tagesjump.testtask.web;

import com.tagesjump.testtask.model.User;
import com.tagesjump.testtask.repository.UserRepository;
import com.tagesjump.testtask.web.exception.BasicInfo;
import com.tagesjump.testtask.web.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/{id}")
    public User getById(@PathVariable Integer id) {
        log.info("get user with id = {}", id);
        User user = userRepository.getById(id);
        if (user == null) {
            log.warn("not found user with id = {}", id);
            throw new NotFoundException(HttpStatus.NOT_FOUND, "User is not found with id = " + id);
        }
        return user;
    }

    @PostMapping(value = "/{id}")
    public void update(@PathVariable Integer id, @Validated(BasicInfo.class) @RequestBody User user) {
        log.info("update user with id = {}", id);
        userRepository.update(id, user);
    }
}
