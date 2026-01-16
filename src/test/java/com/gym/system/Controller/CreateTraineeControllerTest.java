package com.gym.system.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.system.controller.TraineeRegistrationController;
import com.gym.system.dto.TraineeRegistrationRequest;
import com.gym.system.dto.TraineeRegistrationResponse;
import com.gym.system.service.GymServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class CreateTraineeControllerTest {

    private MockMvc mockMvc;
    private GymServices facade;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        facade = Mockito.mock(GymServices.class);
        TraineeRegistrationController controller =
                new TraineeRegistrationController(facade);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturn200_WhenValidRequest() throws Exception {
        // Arrange
        TraineeRegistrationRequest request = new TraineeRegistrationRequest();
        request.setFirstName("Lucas");
        request.setLastName("Diaz");

        TraineeRegistrationResponse response = new TraineeRegistrationResponse();
        response.setUsername("Lucas.Diaz");
        response.setPassword("Abc1234567");

        when(facade.createTrainee(Mockito.any()))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                post("/traineeRegistration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("Lucas.Diaz"))
        .andExpect(jsonPath("$.password").value("Abc1234567"));
    }
}
