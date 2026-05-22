package com.example.demo.controller;

import com.example.demo.entity.Direction;
import com.example.demo.repository.DirectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class DirectionControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DirectionRepository directionRepository;

    private Direction savedDirection;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        Direction direction = new Direction();
        direction.setName("Фортепиано");
        direction.setBasePricePerHalfHour(500.0);
        this.savedDirection = directionRepository.save(direction);
    }

    @Test
    void shouldGetAllDirections() throws Exception {
        mockMvc.perform(get("/api/directions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Фортепиано"));
    }

    @Test
    void shouldGetDirectionById() throws Exception {
        mockMvc.perform(get("/api/directions/" + savedDirection.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Фортепиано"));
    }

    @Test
    void shouldCreateDirection() throws Exception {
        String json = """
                {
                    "name": "Вокал",
                    "basePricePerHalfHour": 600.0
                }
                """;

        mockMvc.perform(post("/api/directions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateDirection() throws Exception {
        String json = """
                {
                    "name": "Гитара",
                    "basePricePerHalfHour": 450.0
                }
                """;

        mockMvc.perform(put("/api/directions/" + savedDirection.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteDirection() throws Exception {
        mockMvc.perform(delete("/api/directions/" + savedDirection.getId()))
                .andExpect(status().isOk());
    }
}