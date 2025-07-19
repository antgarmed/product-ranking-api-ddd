package com.example.backendtools.products.infrastructure.api.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GetRankedProductsEndpointIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnCorrectlyRankedAndSerializedProducts() throws Exception {
        // Arrange
        String weightsParam = "sales:0.8,stock:0.2";

        // Act
        ResultActions result = mockMvc.perform(get("/products")
                .queryParam("weights", weightsParam)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(6)) // Debe devolver los 6 productos
                // Comprobamos los 3 primeros productos y su orden
                .andExpect(jsonPath("$[0].product.id").value(5)) // Product 5, score: (650*0.8)+(1*0.2) = 520.2
                .andExpect(jsonPath("$[0].score").value(520.2))
                .andExpect(jsonPath("$[1].product.id").value(1)) // Product 1, score: (100*0.8)+(2*0.2) = 80.4
                .andExpect(jsonPath("$[1].score").value(80.4))
                .andExpect(jsonPath("$[2].product.id").value(3)) // Product 3, score: (80*0.8)+(3*0.2) = 64.6
                .andExpect(jsonPath("$[2].score").value(64.6));
    }
}
