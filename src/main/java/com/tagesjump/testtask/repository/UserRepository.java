package com.tagesjump.testtask.repository;

import com.tagesjump.testtask.model.User;

public interface UserRepository {
    User getById(int id);

    void update(int id, User user);
}