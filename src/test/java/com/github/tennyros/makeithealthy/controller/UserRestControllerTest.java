package com.github.tennyros.makeithealthy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tennyros.makeithealthy.dto.request.UserRequest;
import com.github.tennyros.makeithealthy.dto.response.UserResponse;
import com.github.tennyros.makeithealthy.entity.Gender;
import com.github.tennyros.makeithealthy.entity.Goal;
import com.github.tennyros.makeithealthy.http.rest.UserRestController;
import com.github.tennyros.makeithealthy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "test@example.com";
    private static final String NAME = "John";
    private static final int AGE = 30;
    private static final double WEIGHT = 80.0;
    private static final double HEIGHT = 180.0;
    private static final double NORM = 2500.0;
    private static final Goal GOAL = Goal.MAINTENANCE;
    private static final Gender GENDER = Gender.MALE;

    private final UserResponse response = new UserResponse(ID, NAME,
            EMAIL, AGE, GENDER, NORM, GOAL
    );
    private final UserRequest request = new UserRequest(NAME, EMAIL,
            AGE, GENDER, WEIGHT, HEIGHT, GOAL);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void createUser_shouldReturnCreated() throws Exception {
        given(userService.createUser(any())).willReturn(response);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/users/1"));
    }

    @Test
    void getUser_shouldReturnResponse() throws Exception {
        given(userService.getUser(ID)).willReturn(Optional.of(response));

        mockMvc.perform(get("/api/v1/users/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID));
    }

    @Test
    void getUser_whenNotFound_shouldReturn404() throws Exception {
        given(userService.getUser(ID)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "name": "John Doe",
                                        "email": "",
                                        "age": 30,
                                        "gender": "MALE",
                                        "weight": 75.0,
                                        "height": 180.0,
                                        "goal": "MAINTENANCE"
                                    }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Validation failed"))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").value("email"))
                .andExpect(jsonPath("$.errors[0].message").exists())
                .andExpect(jsonPath("$.errors[0].rejectedValue").value(""));
    }

}

