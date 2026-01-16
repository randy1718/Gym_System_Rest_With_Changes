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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.gym.system.controller.GetTrainingTypesController;
import com.gym.system.dto.TrainingTypesResponse;
import com.gym.system.model.TrainingType;
import com.gym.system.service.GymServices;

public class GetTrainingTypesControllerTest {

    private MockMvc mockMvc;
    private GymServices facade;

    @BeforeEach
    void setup() {
        facade = Mockito.mock(GymServices.class);
        GetTrainingTypesController controller =
                new GetTrainingTypesController(facade);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void shouldReturn200_WhenValidRequest() throws Exception {
        // Arrange

        TrainingType tt1 = new TrainingType();
        tt1.setName("Cardio");

        TrainingType tt2 = new TrainingType();
        tt2.setName("Stregth");

        TrainingType tt3 = new TrainingType();
        tt3.setName("Crossfit");

        List<TrainingType> trainingTypes = new ArrayList<>();
        trainingTypes.add(tt1);
        trainingTypes.add(tt2);
        trainingTypes.add(tt3);
        
        TrainingTypesResponse response = new TrainingTypesResponse();
        response.setTrainingTypes(trainingTypes);

        when(facade.getTrainingTypes())
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                get("/trainingTypes")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.trainingTypes").isArray())
        .andExpect(jsonPath("$.trainingTypes.length()").value(3))
        .andExpect(jsonPath("$.trainingTypes[0].name").value("Cardio"))
        .andExpect(jsonPath("$.trainingTypes[1].name").value("Stregth"))
        .andExpect(jsonPath("$.trainingTypes[2].name").value("Crossfit"));
    }
}
