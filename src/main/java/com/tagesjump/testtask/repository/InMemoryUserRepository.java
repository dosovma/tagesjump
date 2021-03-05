package com.tagesjump.testtask.repository;

import com.tagesjump.testtask.model.User;
import com.tagesjump.testtask.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Slf4j
public class InMemoryUserRepository implements UserRepository {
    private final AtomicInteger counter = new AtomicInteger(1000);

    private final Map<Integer, User> map = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        Util.populateMap(map);
    }

    @Override
    public User getById(int id) {
        log.info("get from memory user with id = {}", id);
        return map.get(id);
    }

    @Override
    public void update(int id, User user) {
        log.info("update in memory user with id = {}", id);
        User userFromMemory = map.get(id);
        if (user.getName() != null) {
            userFromMemory.setName(user.getName());
        }
        if (user.getAge() != null) {
            userFromMemory.setAge(user.getAge());
        }
    }
}
