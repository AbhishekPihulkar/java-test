package com.javaTest.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaTest.java.dto.BfhlRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BfhlControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @DisplayName("Test POST /bfhl with valid request")
    void testPostBfhlValidRequest() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "1", "22", "$", "B", "7"));
        
        mockMvc.perform(post("/bfhl")
                .header("X-Request-Id", "REQ-1001")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.request_id").value("REQ-1001"))
                .andExpect(jsonPath("$.odd_numbers").isArray())
                .andExpect(jsonPath("$.even_numbers").isArray())
                .andExpect(jsonPath("$.alphabets").isArray())
                .andExpect(jsonPath("$.special_characters").isArray())
                .andExpect(jsonPath("$.sum").exists())
                .andExpect(jsonPath("$.largest_number").exists())
                .andExpect(jsonPath("$.smallest_number").exists())
                .andExpect(jsonPath("$.alphabet_count").isNumber())
                .andExpect(jsonPath("$.number_count").isNumber())
                .andExpect(jsonPath("$.special_character_count").isNumber())
                .andExpect(jsonPath("$.contains_duplicates").isBoolean())
                .andExpect(jsonPath("$.processing_time_ms").isNumber());
    }
    
    @Test
    @DisplayName("Test POST /bfhl without X-Request-Id header")
    void testPostBfhlWithoutRequestId() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "1"));
        
        mockMvc.perform(post("/bfhl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.request_id").exists());
    }
    
    @Test
    @DisplayName("Test POST /bfhl with null data field")
    void testPostBfhlWithNullData() throws Exception {
        String jsonRequest = "{\"data\": null}";
        
        mockMvc.perform(post("/bfhl")
                .header("X-Request-Id", "REQ-NULL")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false));
    }
    
    @Test
    @DisplayName("Test POST /bfhl with invalid JSON")
    void testPostBfhlWithInvalidJson() throws Exception {
        String invalidJson = "{invalid json}";
        
        mockMvc.perform(post("/bfhl")
                .header("X-Request-Id", "REQ-INVALID")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Test POST /bfhl with alphanumeric strings")
    void testPostBfhlWithAlphanumeric() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A1B2", "100", "#", "Test123", "Z", "55"));
        
        mockMvc.perform(post("/bfhl")
                .header("X-Request-Id", "REQ-1002")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.request_id").value("REQ-1002"))
                .andExpect(jsonPath("$.alphabet_count").value(7))
                .andExpect(jsonPath("$.number_count").value(2));
    }
    
    @Test
    @DisplayName("Test POST /bfhl with duplicates")
    void testPostBfhlWithDuplicates() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList("10", "10", "A", "A", "", null, "&", "5"));
        
        mockMvc.perform(post("/bfhl")
                .header("X-Request-Id", "REQ-1003")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.contains_duplicates").value(true))
                .andExpect(jsonPath("$.unique_element_count").value(4));
    }
    
    @Test
    @DisplayName("Test POST /bfhl with negative and decimal numbers")
    void testPostBfhlWithNegativeAndDecimal() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList("-10", "25.5", "-100.75", "B", "@", "5", "A9"));
        
        mockMvc.perform(post("/bfhl")
                .header("X-Request-Id", "REQ-1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.largest_number").value("25.5"))
                .andExpect(jsonPath("$.smallest_number").value("-100.75"))
                .andExpect(jsonPath("$.sum").value("-80.25"));
    }
    
    @Test
    @DisplayName("Test POST /bfhl with comprehensive data")
    void testPostBfhlComprehensive() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList(
            "ABC", "123", "A1B2", "$", "%", "-50", "0", "xyz", "", null, "999", "Test99", "&"
        ));
        
        mockMvc.perform(post("/bfhl")
                .header("X-Request-Id", "REQ-1005")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.alphabet_frequency").exists())
                .andExpect(jsonPath("$.sorted_numbers").exists())
                .andExpect(jsonPath("$.vowel_count").exists())
                .andExpect(jsonPath("$.consonant_count").exists())
                .andExpect(jsonPath("$.longest_alphabetic_value").exists())
                .andExpect(jsonPath("$.shortest_alphabetic_value").exists())
                .andExpect(jsonPath("$.summary").exists())
                .andExpect(jsonPath("$.summary.total_elements_received").value(13));
    }
    
    @Test
    @DisplayName("Test GET /bfhl health check")
    void testGetBfhlHealthCheck() throws Exception {
        mockMvc.perform(get("/bfhl"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("operation_code")));
    }
    
    @Test
    @DisplayName("Test POST /bfhl with empty array")
    void testPostBfhlWithEmptyArray() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList());
        
        mockMvc.perform(post("/bfhl")
                .header("X-Request-Id", "REQ-EMPTY")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.alphabet_count").value(0))
                .andExpect(jsonPath("$.number_count").value(0));
    }
}
