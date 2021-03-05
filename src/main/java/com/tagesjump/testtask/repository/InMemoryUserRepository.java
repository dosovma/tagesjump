package com.tagesjump.testtask.repository;

import com.tagesjump.testtask.model.User;
import com.tagesjump.testtask.util.Util;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final AtomicInteger counter = new AtomicInteger(1000);

    private final Map<Integer, User> map = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        Util.populateMap(map);
    }

    @Override
    public User getById(int id) {
        return map.get(id);
    }

    @Override
    public void update(int id, User user) {
        User userFromMemory = map.get(id);
        userFromMemory.setName(user.getName());
        userFromMemory.setAge(user.getAge());
    }
}
