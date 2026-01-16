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
import com.gym.system.controller.ActivateDeactivateTrainerController;
import com.gym.system.dto.ActivateDeactivateTrainerRequest;
import com.gym.system.service.GymServices;

public class ActivateDeactivateTrainerControllerTest {
    private MockMvc mockMvc;
    private GymServices facade;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        facade = Mockito.mock(GymServices.class);
        ActivateDeactivateTrainerController controller =
                new ActivateDeactivateTrainerController(facade);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturn200_WhenValidRequest() throws Exception {

        ActivateDeactivateTrainerRequest request = new ActivateDeactivateTrainerRequest();
        request.setUsername("Luisa.Vallez");
        request.setIsActive(true);

        when(facade.activateDeactivateTrainer(Mockito.any()))
                .thenReturn(true);

        // Act + Assert
        mockMvc.perform(
                patch("/activateDeactivateTrainer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk());
    }
}
