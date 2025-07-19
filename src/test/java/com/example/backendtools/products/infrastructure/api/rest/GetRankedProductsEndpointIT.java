package com.example.backendtools.products.infrastructure.api.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Comparator;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class GetRankedProductsEndpointIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnCorrectlyRankedAndSerializedProducts() throws Exception {
        // Act
        String weightsParam = "sales:0.8,stock:0.2";
        ResultActions result = mockMvc.perform(get("/products")
                .queryParam("weights", weightsParam)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        String response = result
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Double> scores = JsonPath.read(response, "$[*].score");
        assertThat(scores)
                .as("Los scores deben ir de mayor a menor")
                .isSortedAccordingTo(Comparator.reverseOrder());

        assertThat(JsonPath.<Integer>read(response, "$[0].product.id")).isEqualTo(5);
        assertThat(JsonPath.<Double>read(response, "$[0].score")).isCloseTo(520.2, within(1e-9));
    }
}
