package com.javaTest.java.service;

import com.javaTest.java.dto.BfhlRequest;
import com.javaTest.java.dto.BfhlResponse;

public interface BfhlService {
    
    /**
     * Process the BFHL request and return categorized data
     * 
     * @param request The BFHL request containing data array
     * @param requestId The X-Request-Id header value
     * @return BfhlResponse with categorized and processed data
     */
    BfhlResponse processData(BfhlRequest request, String requestId);
}
