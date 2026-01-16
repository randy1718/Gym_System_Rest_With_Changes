package com.gym.system.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import com.gym.system.controller.GetTraineeController;
import com.gym.system.dto.TraineeProfileRequest;
import com.gym.system.dto.TraineeProfileResponse;
import com.gym.system.dto.TraineeTrainersList;
import com.gym.system.service.GymServices;

public class GetTraineeControllerTest {
    private MockMvc mockMvc;
    private GymServices facade;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        facade = Mockito.mock(GymServices.class);
        GetTraineeController controller = new GetTraineeController(facade);

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
        TraineeProfileRequest request = new TraineeProfileRequest();
        request.setUsername("Julio.Domingo");

        TraineeProfileResponse response = new TraineeProfileResponse();
        response.setFirstName("Julio");
        response.setLastName("Domingo");
        response.setAddress("Calle 12 #34-56");
        LocalDate dob = LocalDate.of(2000, 12, 12);
        response.setDateOfBirth(dob);
        response.setIsActive(true);
        List<TraineeTrainersList> trainers = new ArrayList<>();
        TraineeTrainersList ttl1 = new TraineeTrainersList();
        ttl1.setFirstName("Mario Hernandez");
        trainers.add(ttl1);
        response.setTrainers(trainers);

        when(facade.getTrainee(Mockito.any()))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                get("/getTrainee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Julio"))
                .andExpect(jsonPath("$.lastName").value("Domingo"))
                .andExpect(jsonPath("$.address").value("Calle 12 #34-56"))
                .andExpect(jsonPath("$.dateOfBirth").value("2000-12-12"))
                .andExpect(jsonPath("$.isActive").value(true))
                .andExpect(jsonPath("$.trainers.length()").value(1));
    }
}
