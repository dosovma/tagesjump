package com.tagesjump.testtask.repository;

import com.tagesjump.testtask.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.tagesjump.testtask.UserTestData.getNameAndAgeUpdated;
import static com.tagesjump.testtask.UserTestData.mockUser1;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InMemoryUserRepositoryTest {
    private final InMemoryUserRepository userRepository;

    @Autowired
    public InMemoryUserRepositoryTest(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        userRepository.init();
    }

    @Test
    void getById() {
        User userFormDB = userRepository.getById(mockUser1.getId());
        assertThat(userFormDB).isEqualTo(mockUser1);
    }

    @Test
    void update() {
        User user = getNameAndAgeUpdated(mockUser1);
        userRepository.update(user.getId(), user);
        User userFromDB = userRepository.getById(user.getId());
        assertThat(userFromDB).isEqualTo(getNameAndAgeUpdated(mockUser1));
    }
}