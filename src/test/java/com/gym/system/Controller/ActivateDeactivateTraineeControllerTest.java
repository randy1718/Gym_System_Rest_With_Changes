package com.gym.system.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.system.controller.ActivateDeactivateTraineeController;
import com.gym.system.dto.ActivateDeactivateTraineeRequest;
import com.gym.system.service.GymServices;

public class ActivateDeactivateTraineeControllerTest {
    private MockMvc mockMvc;
    private GymServices facade;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        facade = Mockito.mock(GymServices.class);
        ActivateDeactivateTraineeController controller =
                new ActivateDeactivateTraineeController(facade);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturn200_WhenValidRequest() throws Exception {

        ActivateDeactivateTraineeRequest request = new ActivateDeactivateTraineeRequest();
        request.setUsername("Jonny.Diaz");
        request.setIsActive(false);

        when(facade.activateDeactivateTrainee(Mockito.any()))
                .thenReturn(true);

        // Act + Assert
        mockMvc.perform(
                patch("/activateDeactivateTrainee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk());
    }
}
