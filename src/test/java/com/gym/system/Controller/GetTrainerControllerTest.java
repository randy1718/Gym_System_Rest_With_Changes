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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gym.system.controller.GetTrainerController;
import com.gym.system.dto.TrainerProfileRequest;
import com.gym.system.dto.TrainerProfileResponse;
import com.gym.system.dto.TrainerTraineesList;
import com.gym.system.service.GymServices;

public class GetTrainerControllerTest {
    private MockMvc mockMvc;
    private GymServices facade;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        facade = Mockito.mock(GymServices.class);
        GetTrainerController controller = new GetTrainerController(facade);

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
        TrainerProfileRequest request = new TrainerProfileRequest();
        request.setUsername("Mary.Valetyn");

        TrainerProfileResponse response = new TrainerProfileResponse();
        response.setFirstName("Mary");
        response.setLastName("Valetyn");
        response.setIsActive(true);
        response.setSpecialization("Cardio");
        List<TrainerTraineesList> trainees = new ArrayList<>();
        TrainerTraineesList ttl1 = new TrainerTraineesList();
        ttl1.setFirstName("Gustavo Pereira");
        trainees.add(ttl1);
        response.setTrainees(trainees);
        when(facade.getTrainer(Mockito.any()))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                get("/getTrainer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Mary"))
                .andExpect(jsonPath("$.lastName").value("Valetyn"))
                .andExpect(jsonPath("$.isActive").value(true))
                .andExpect(jsonPath("$.specialization").value("Cardio"))
                .andExpect(jsonPath("$.trainees.length()").value(1));
    }
}
