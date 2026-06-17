package com.javaTest.java.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BfhlRequest {
    
    @NotNull(message = "Data array cannot be null")
    private List<String> data;
}
