package com.javaTest.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    
    @JsonProperty("is_success")
    private boolean isSuccess;
    
    private String message;
    
    private String error;
    
    private long timestamp;
}
