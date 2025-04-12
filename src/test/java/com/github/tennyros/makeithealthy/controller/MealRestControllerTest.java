package com.github.tennyros.makeithealthy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tennyros.makeithealthy.dto.request.MealRequest;
import com.github.tennyros.makeithealthy.dto.response.MealResponse;
import com.github.tennyros.makeithealthy.dto.response.NutritionalInfoDto;
import com.github.tennyros.makeithealthy.http.rest.MealRestController;
import com.github.tennyros.makeithealthy.service.MealService;
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

@WebMvcTest(MealRestController.class)
class MealRestControllerTest {

    private static final Long ID = 1L;
    private static final String NAME = "Fish and Chips";
    private static final double CALORIES = 500.0;
    private static final double PROTEINS = 20.0;
    private static final double CARBS = 20.0;
    private static final double FATS = 20.0;

    private final NutritionalInfoDto nutritional =
            new NutritionalInfoDto(CALORIES, PROTEINS, CARBS, FATS);
    private final MealResponse response = new MealResponse(ID, NAME, nutritional);
    private final MealRequest request = new MealRequest(NAME, CALORIES, PROTEINS, CARBS, FATS);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MealService mealService;

    @Test
    void createMeal_shouldReturnCreated() throws Exception {
        given(mealService.createMeal(any())).willReturn(response);

        mockMvc.perform(post("/api/v1/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/meals/1"));
    }

    @Test
    void getMeal_shouldReturnResponse() throws Exception {
        given(mealService.getMeal(ID)).willReturn(Optional.of(response));

        mockMvc.perform(get("/api/v1/meals/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID));
    }

    @Test
    void getMeal_whenNotFound_shouldReturn404() throws Exception {
        given(mealService.getMeal(ID)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/meals/1"))
                .andExpect(status().isNotFound());
    }
}
