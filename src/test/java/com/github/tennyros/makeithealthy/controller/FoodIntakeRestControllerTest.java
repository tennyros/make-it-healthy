package com.github.tennyros.makeithealthy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tennyros.makeithealthy.dto.request.FoodIntakeRequest;
import com.github.tennyros.makeithealthy.dto.response.FoodIntakeResponse;
import com.github.tennyros.makeithealthy.http.rest.FoodIntakeRestController;
import com.github.tennyros.makeithealthy.service.FoodIntakeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.github.tennyros.makeithealthy.entity.MealType.DINNER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FoodIntakeRestController.class)
class FoodIntakeRestControllerTest {

    private static final Long ID = 1L;

    private final FoodIntakeResponse response = new FoodIntakeResponse(ID, DINNER);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FoodIntakeService foodIntakeService;

    @Test
    void createFoodIntake_shouldReturnOk() throws Exception {
        FoodIntakeRequest request = new FoodIntakeRequest(ID, DINNER, List.of(100L, 101L));

        given(foodIntakeService.createFoodIntake(any())).willReturn(response);

        mockMvc.perform(post("/api/v1/intakes")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()));
    }

    @Test
    void getFoodIntake_shouldReturnResponse() throws Exception {
        given(foodIntakeService.getFoodIntake(ID)).willReturn(Optional.of(response));

        mockMvc.perform(get("/api/v1/intakes/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID));
    }

    @Test
    void getFoodIntake_whenNotFound_shouldReturn404() throws Exception {
        given(foodIntakeService.getFoodIntake(ID)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/intakes/{id}", ID))
                .andExpect(status().isNotFound());
    }
}

