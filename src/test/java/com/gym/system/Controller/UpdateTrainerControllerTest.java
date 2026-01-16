package com.gym.system.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gym.system.controller.UpdateTrainerController;
import com.gym.system.dto.TrainerTraineesList;
import com.gym.system.dto.UpdateTrainerRequest;
import com.gym.system.dto.UpdateTrainerResponse;
import com.gym.system.service.GymServices;

public class UpdateTrainerControllerTest {
    private MockMvc mockMvc;
    private GymServices facade;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        facade = Mockito.mock(GymServices.class);
        UpdateTrainerController controller =
                new UpdateTrainerController(facade);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void shouldReturn200_WhenValidRequest() throws Exception {

        UpdateTrainerRequest request = new UpdateTrainerRequest();
        request.setUsername("Alfredo.Cavalli");
        request.setFirstName("Alfredo");
        request.setLastName("Cavalli");
        request.setSpecialization("Cardio");
        request.setIsActive(true);

        UpdateTrainerResponse response = new UpdateTrainerResponse();
        response.setUsername("Alfredo.Cavalli");
        response.setFirstName("Alfredo");
        response.setLastName("Cavalli");
        response.setSpecialization("Cardio");
        response.setIsActive(true);
        List<TrainerTraineesList> traineesList = new ArrayList<>();
        response.setTrainees(traineesList);

        when(facade.updateTrainer(Mockito.any()))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                put("/updateTrainer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk());
    }
}
