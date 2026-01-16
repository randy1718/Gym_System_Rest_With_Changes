package com.gym.system.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gym.system.controller.UpdateTraineeTrainersListController;
import com.gym.system.dto.TraineeTrainersList;
import com.gym.system.dto.UpdateTrainersListRequest;
import com.gym.system.dto.UpdateTrainersListResponse;
import com.gym.system.dto.UpdatedTrainersList;
import com.gym.system.service.GymServices;

public class UpdateTraineeTrainersListControllerTest {
    private MockMvc mockMvc;
    private GymServices facade;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        facade = Mockito.mock(GymServices.class);
        UpdateTraineeTrainersListController controller = new UpdateTraineeTrainersListController(facade);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setMessageConverters(jacksonConverter)
                .build();
    }

    @Test
    void shouldReturn200_WhenValidRequest() throws Exception {
        // Arrange
        UpdateTrainersListRequest request = new UpdateTrainersListRequest();
        request.setUsername("Julio.Domingo");
        List<UpdatedTrainersList> trainers = new ArrayList<>();
        UpdatedTrainersList ttl1 = new UpdatedTrainersList();
        ttl1.setUsername("Carlos.Montoya");
        trainers.add(ttl1);
        request.setTrainers(trainers);

        UpdateTrainersListResponse response = new UpdateTrainersListResponse();
        List<TraineeTrainersList> updatedTrainers = new ArrayList<>();
        TraineeTrainersList uttl1 = new TraineeTrainersList();
        uttl1.setUsername("Carlos.Montoya");
        uttl1.setFirstName("Carlos");
        uttl1.setLastName("Montoya");
        uttl1.setSpecialization("Fitness");
        updatedTrainers.add(uttl1);
        response.setUpdatedTrainers(updatedTrainers);

        when(facade.UpdateTraineeTrainersList(Mockito.any()))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                put("/updateTraineeTrainersList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updatedTrainers.length()").value(1))
                .andExpect(jsonPath("$.updatedTrainers[0].firstName").value("Carlos"))
                .andExpect(jsonPath("$.updatedTrainers[0].lastName").value("Montoya"))
                .andExpect(jsonPath("$.updatedTrainers[0].specialization").value("Fitness"))
                .andExpect(jsonPath("$.updatedTrainers[0].username").value("Carlos.Montoya"));
    }
}
