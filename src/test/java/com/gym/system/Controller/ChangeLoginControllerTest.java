package com.gym.system.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.system.controller.ChangeLoginController;
import com.gym.system.dto.ChangeLoginRequest;
import com.gym.system.service.GymServices;

public class ChangeLoginControllerTest {
    private MockMvc mockMvc;
    private GymServices facade;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        facade = Mockito.mock(GymServices.class);
        ChangeLoginController controller =
                new ChangeLoginController(facade);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturn200_WhenValidRequest() throws Exception {

        ChangeLoginRequest request = new ChangeLoginRequest();
        request.setUsername("Fabian.Gomez");
        request.setOldPassword("Fagobian18");
        request.setNewPassword("dfslalc123_dvd");

        when(facade.changeLogin(Mockito.any()))
                .thenReturn(true);

        // Act + Assert
        mockMvc.perform(
                put("/changeLogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk());
    }
}
