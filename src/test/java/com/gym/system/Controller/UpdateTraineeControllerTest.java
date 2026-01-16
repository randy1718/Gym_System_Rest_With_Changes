package com.gym.system.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import com.gym.system.controller.UpdateTraineeController;
import com.gym.system.dto.TraineeTrainersList;
import com.gym.system.dto.UpdateTraineeRequest;
import com.gym.system.dto.UpdateTraineeResponse;
import com.gym.system.service.GymServices;

public class UpdateTraineeControllerTest {
    private MockMvc mockMvc;
    private GymServices facade;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        facade = Mockito.mock(GymServices.class);
        UpdateTraineeController controller =
                new UpdateTraineeController(facade);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void shouldReturn200_WhenValidRequest() throws Exception {

        UpdateTraineeRequest request = new UpdateTraineeRequest();
        request.setUsername("Roberto.Cavalli");
        request.setFirstName("Roberto");
        request.setLastName("Cavalli");
        //request.setAddress("Street 5th 12 30");
        request.setIsActive(true);
        //request.setDateOfBirth("2000-09-19");

        UpdateTraineeResponse response = new UpdateTraineeResponse();
        response.setUsername("Roberto.Cavalli");
        response.setFirstName("Roberto");
        response.setLastName("Cavalli");
        response.setAddress("Street 5th 12 30");
        response.setIsActive(true);
        LocalDate dob = LocalDate.of(2000, 9, 19);
        response.setDateOfBirth(dob);
        List<TraineeTrainersList> trainerLists = new ArrayList<>();
        response.setTrainers(trainerLists); 

        when(facade.updateTrainee(Mockito.any()))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                put("/updateTrainee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk());
    }
}
