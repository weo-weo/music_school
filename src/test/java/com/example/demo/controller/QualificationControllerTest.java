package com.example.demo.controller;

import com.example.demo.entity.Qualification;
import com.example.demo.repository.QualificationRepository;
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
class QualificationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private QualificationRepository qualificationRepository;

    private Qualification savedQualification;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        Qualification qualification = new Qualification();
        qualification.setName("Высшая категория");
        qualification.setSalaryCoefficient(1.5);
        this.savedQualification = qualificationRepository.save(qualification);
    }

    @Test
    void shouldGetAllQualifications() throws Exception {
        mockMvc.perform(get("/api/qualifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Высшая категория"));
    }

    @Test
    void shouldGetQualificationById() throws Exception {
        mockMvc.perform(get("/api/qualifications/" + savedQualification.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Высшая категория"));
    }

    @Test
    void shouldCreateQualification() throws Exception {
        String json = """
                {
                    "name": "Первая категория",
                    "salaryCoefficient": 1.2
                }
                """;

        mockMvc.perform(post("/api/qualifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateQualification() throws Exception {
        String json = """
                {
                    "name": "Обновленная категория",
                    "salaryCoefficient": 1.8
                }
                """;

        mockMvc.perform(put("/api/qualifications/" + savedQualification.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteQualification() throws Exception {
        mockMvc.perform(delete("/api/qualifications/" + savedQualification.getId()))
                .andExpect(status().isOk());
    }
}