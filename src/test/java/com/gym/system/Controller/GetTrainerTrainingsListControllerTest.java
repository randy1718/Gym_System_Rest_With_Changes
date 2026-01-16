package com.gym.system.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import com.gym.system.controller.GetTrainerTrainingsListController;
import com.gym.system.dto.TrainerTrainingList;
import com.gym.system.dto.TrainerTrainingsListRequest;
import com.gym.system.dto.TrainerTrainingsListResponse;
import com.gym.system.service.GymServices;

public class GetTrainerTrainingsListControllerTest {
    private MockMvc mockMvc;
    private GymServices facade;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        facade = Mockito.mock(GymServices.class);
        GetTrainerTrainingsListController controller =
                new GetTrainerTrainingsListController(facade);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void shouldReturn200_WhenValidRequest() throws Exception {

        TrainerTrainingsListRequest request = new TrainerTrainingsListRequest();
        request.setUsername("Camilo.Diaz");
        request.setFrom("2024-01-01");
        request.setTo("2025-12-31");
        request.setTraineeName("Veronica Leon");

        TrainerTrainingsListResponse response = new TrainerTrainingsListResponse();
        List<TrainerTrainingList> trainings = new ArrayList<>();
        TrainerTrainingList ttl1 = new TrainerTrainingList();
        ttl1.setTraineeName("Veronica Leon");
        ttl1.setTrainingDate("2024-05-20");
        ttl1.setDuration(60);
        ttl1.setTrainingName("Morning Strength Session - Veronica");
        ttl1.setTrainingType("Strength");
        trainings.add(ttl1);
        response.setTrainings(trainings);

        when(facade.getTrainerTrainingsList(Mockito.any()))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                get("/getTrainerTrainingsList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.trainings.length()").value(1))
        .andExpect(jsonPath("$.trainings[0].traineeName").value("Veronica Leon"))
        .andExpect(jsonPath("$.trainings[0].trainingDate").value("2024-05-20"))
        .andExpect(jsonPath("$.trainings[0].duration").value(60));
    }
}
