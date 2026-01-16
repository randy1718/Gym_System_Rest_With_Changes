package com.gym.system.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.system.controller.AddTrainingController;
import com.gym.system.dto.AddTrainingRequest;
import com.gym.system.service.GymServices;

public class AddTrainingControllerTest {
    private MockMvc mockMvc;
    private GymServices facade;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        facade = Mockito.mock(GymServices.class);
        AddTrainingController controller =
                new AddTrainingController(facade);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturn200_WhenValidRequest() throws Exception {

        AddTrainingRequest request = new AddTrainingRequest();
        request.setTraineeUsername("Alex.Perez");
        request.setTrainerUsername("Ana.Lopez");
        request.setTrainingDate("2026-01-08 10:00:00");
        request.setTrainingDuration(60);
        request.setTrainingName("Full Body Workout");

        when(facade.addTraining(Mockito.any()))
                .thenReturn(true);

        // Act + Assert
        mockMvc.perform(
                post("/addTraining")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk());
    }
}
