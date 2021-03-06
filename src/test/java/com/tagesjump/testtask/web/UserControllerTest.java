package com.tagesjump.testtask.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tagesjump.testtask.model.User;
import com.tagesjump.testtask.repository.InMemoryUserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.tagesjump.testtask.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private final String REST_URL = "/user/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InMemoryUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.init();
    }

    @Test
    void getById() throws Exception {
        ResultActions action = mockMvc.perform(get(REST_URL + mockUser1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        User userFromJSON = objectMapper.readValue(action.andReturn().getResponse().getContentAsString(), User.class);
        assertThat(userFromJSON).isEqualTo(mockUser1);
    }

    @Test
    void getByIdNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + NOT_FOUND_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.containsString("User is not found with id = " + NOT_FOUND_ID)));
    }

    @Test
    void update() throws Exception {
        User user = getNameAndAgeUpdated(mockUser1);
        mockMvc.perform(post(REST_URL + mockUser1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        )
                .andExpect(status().isOk());

        User userAfterUpdate = userRepository.getById(mockUser1.getId());
        assertThat(userAfterUpdate).isEqualTo(user);
    }

    @Test
    void updateOnlyAge() throws Exception {
        mockMvc.perform(post(REST_URL + mockUser1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJsonToUpdateUserAge())
        )
                .andExpect(status().isOk());

        User userAfterUpdate = userRepository.getById(mockUser1.getId());
        assertThat(userAfterUpdate).isEqualTo(getAgeUpdated(mockUser1));
    }

    @Test
    void updateIgnoringIdAndToken() throws Exception {
        mockMvc.perform(post(REST_URL + mockUser1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getFullUpdated()))
        )
                .andExpect(status().isOk());

        User userAfterUpdate = userRepository.getById(mockUser1.getId());
        assertThat(userAfterUpdate).isEqualTo(getNameAndAgeUpdated(mockUser1));
    }

    @Test
    void updateThrowBindingException() throws Exception {
        mockMvc.perform(post(REST_URL + mockUser1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getBadJson())
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.containsString("Age must be positive")))
                .andExpect(jsonPath("$.message", Matchers.containsString("Name min length is 2")));
    }

    @Test
    void methodNotAllowed() throws Exception {
        mockMvc.perform(put(REST_URL + mockUser1.getId()))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.containsString("Request method 'PUT' not supported")));
    }
}