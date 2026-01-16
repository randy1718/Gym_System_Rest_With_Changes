package com.gym.system.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.gym.system.controller.GetTraineeTrainingsListController;
import com.gym.system.dto.TraineeTrainingsListRequest;
import com.gym.system.dto.TraineeTrainingsListResponse;
import com.gym.system.dto.TrainingList;
import com.gym.system.service.GymServices;

public class GetTraineeTrainingsListControllerTest {
    private MockMvc mockMvc;
    private GymServices facade;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        facade = Mockito.mock(GymServices.class);
        GetTraineeTrainingsListController controller =
                new GetTraineeTrainingsListController(facade);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void shouldReturn200_WhenValidRequest() throws Exception {

        TraineeTrainingsListRequest request = new TraineeTrainingsListRequest();
        request.setUsername("Rose.Smith");
        request.setFrom("2023-01-01");
        request.setTo("2023-12-31");
        request.setTrainerName("John Carter");
        request.setTrainingType("Cardio");

        TraineeTrainingsListResponse response = new TraineeTrainingsListResponse();

        List<TrainingList> trainings = new ArrayList<>();
        response.setTrainings(trainings);

        when(facade.getTraineeTrainingsList(Mockito.any()))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                get("/getTraineeTrainingsList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk());
    }
}
