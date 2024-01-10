package com.epam.controller;

import com.epam.client.MainServiceClient;
import com.epam.dto.TrainerCreateDTO;
import com.epam.dto.TrainerDTO;
import com.epam.dto.TrainerWorkloadUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
class SecondaryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MainServiceClient mainServiceClient;

    @Test
    void summary() throws Exception {
        mockMvc.perform(get("/workload/summary/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void byUsername() throws Exception {
        TrainerDTO dto = mainServiceClient.createTrainer(new TrainerCreateDTO("ByUsername", "Test", 1));
        mockMvc.perform(get("/workload/summary/by-username?username=%s".formatted(dto.username())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void update() throws Exception {
        TrainerDTO dto = mainServiceClient.createTrainer(new TrainerCreateDTO("Update", "Test", 1));
        TrainerWorkloadUpdateDTO updateDTO = new TrainerWorkloadUpdateDTO("NewUpdate", "NewLastName", true, LocalDate.now(), 10);
        mockMvc.perform(put("/workload/summary/by-username?username=%s".formatted(dto.username()))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(updateDTO.firstName()))
                .andExpect(jsonPath("$.lastName").value(updateDTO.lastName()))
                .andExpect(jsonPath("$.active").value(updateDTO.active()))
                .andExpect(jsonPath("$.duration").value(updateDTO.duration()));
    }
}