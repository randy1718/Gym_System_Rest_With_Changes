package com.gym.system.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.system.controller.TrainerRegistrationController;
import com.gym.system.dto.TrainerRegistrationRequest;
import com.gym.system.dto.TrainerRegistrationResponse;
import com.gym.system.service.GymServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class CreateTrainerControllerTest {

    private MockMvc mockMvc;
    private GymServices facade;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        facade = Mockito.mock(GymServices.class);
        TrainerRegistrationController controller =
                new TrainerRegistrationController(facade);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturn200_WhenValidRequest() throws Exception {
        // Arrange
        TrainerRegistrationRequest request = new TrainerRegistrationRequest();
        request.setFirstName("Mario");
        request.setLastName("Hernandez");

        TrainerRegistrationResponse response = new TrainerRegistrationResponse();
        response.setUsername("Mario.Hernandez");
        response.setPassword("1234567qasdAS");

        when(facade.createTrainer(Mockito.any()))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                post("/trainerRegistration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("Mario.Hernandez"))
        .andExpect(jsonPath("$.password").value("1234567qasdAS"));
    }
}
