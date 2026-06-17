package com.javaTest.java.controller;

import com.javaTest.java.dto.BfhlRequest;
import com.javaTest.java.dto.BfhlResponse;
import com.javaTest.java.service.BfhlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bfhl")
@RequiredArgsConstructor
@Slf4j
public class BfhlController {
    
    private final BfhlService bfhlService;
    
    /**
     * POST /bfhl - Process mixed array input and return categorized data
     * 
     * @param requestId X-Request-Id header
     * @param request Request body containing data array
     * @return BfhlResponse with processed data
     */
    @PostMapping
    public ResponseEntity<BfhlResponse> processBfhlData(
            @RequestHeader(value = "X-Request-Id", required = false) String requestId,
            @Valid @RequestBody BfhlRequest request) {
        
        log.info("Received POST /bfhl request with X-Request-Id: {}", requestId);
        
        // Generate request ID if not provided
        if (requestId == null || requestId.trim().isEmpty()) {
            requestId = "REQ-" + System.currentTimeMillis();
            log.info("Generated request ID: {}", requestId);
        }
        
        BfhlResponse response = bfhlService.processData(request, requestId);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /bfhl - Health check endpoint
     * 
     * @return Simple operation code response
     */
    @GetMapping
    public ResponseEntity<String> healthCheck() {
        log.info("Received GET /bfhl health check");
        return ResponseEntity.ok("{\"operation_code\":\"BFHL_API_V1\"}");
    }
    
    /**
     * GET /health - Application health check endpoint
     * 
     * @return Health status response
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        log.info("Received GET /health request");
        return ResponseEntity.ok("{\"status\":\"UP\",\"service\":\"BFHL API\"}");
    }
}
