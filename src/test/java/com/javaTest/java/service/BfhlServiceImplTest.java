package com.javaTest.java.service;

import com.javaTest.java.dto.BfhlRequest;
import com.javaTest.java.dto.BfhlResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BfhlServiceImplTest {
    
    private BfhlServiceImpl bfhlService;
    
    @BeforeEach
    void setUp() {
        bfhlService = new BfhlServiceImpl();
    }
    
    @Test
    @DisplayName("Test basic number and alphabet processing")
    void testBasicProcessing() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "1", "22", "$", "B", "7"));
        
        BfhlResponse response = bfhlService.processData(request, "REQ-1001");
        
        assertTrue(response.isSuccess());
        assertEquals("REQ-1001", response.requestId());
        assertEquals(Arrays.asList("1", "7"), response.oddNumbers());
        assertEquals(Arrays.asList("22"), response.evenNumbers());
        assertEquals(Arrays.asList("A", "B"), response.alphabets());
        assertEquals(Arrays.asList("$"), response.specialCharacters());
        assertEquals("30", response.sum());
        assertEquals("22", response.largestNumber());
        assertEquals("1", response.smallestNumber());
        assertEquals(2, response.alphabetCount());
        assertEquals(3, response.numberCount());
        assertEquals(1, response.specialCharacterCount());
        assertFalse(response.containsDuplicates());
    }
    
    @Test
    @DisplayName("Test alphanumeric string processing")
    void testAlphanumericProcessing() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A1B2", "100", "#", "Test123", "Z", "55"));
        
        BfhlResponse response = bfhlService.processData(request, "REQ-1002");
        
        assertTrue(response.isSuccess());
        assertEquals("REQ-1002", response.requestId());
        assertTrue(response.oddNumbers().contains("55"));
        assertTrue(response.evenNumbers().contains("100"));
        assertTrue(response.alphabets().containsAll(Arrays.asList("A", "B", "T", "E", "S", "T", "Z")));
        assertEquals(7, response.alphabetCount());
        assertEquals(2, response.numberCount());
        assertEquals("155", response.sum());
    }
    
    @Test
    @DisplayName("Test duplicate removal and detection")
    void testDuplicateHandling() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("10", "10", "A", "A", "", null, "&", "5"));
        
        BfhlResponse response = bfhlService.processData(request, "REQ-1003");
        
        assertTrue(response.isSuccess());
        assertTrue(response.containsDuplicates());
        assertEquals(4, response.uniqueElementCount());
        assertEquals(Arrays.asList("5"), response.oddNumbers());
        assertEquals(Arrays.asList("10"), response.evenNumbers());
        assertEquals(Arrays.asList("A"), response.alphabets());
        assertEquals(1, response.alphabetCount());
        assertEquals(2, response.numberCount());
    }
    
    @Test
    @DisplayName("Test negative and decimal number handling")
    void testNegativeAndDecimalNumbers() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("-10", "25.5", "-100.75", "B", "@", "5", "A9"));
        
        BfhlResponse response = bfhlService.processData(request, "REQ-1004");
        
        assertTrue(response.isSuccess());
        assertEquals("25.5", response.largestNumber());
        assertEquals("-100.75", response.smallestNumber());
        assertEquals("-80.25", response.sum());
        assertEquals(4, response.numberCount());
        assertTrue(response.oddNumbers().contains("5"));
        assertTrue(response.evenNumbers().contains("-10"));
    }
    
    @Test
    @DisplayName("Test comprehensive processing with all features")
    void testComprehensiveProcessing() {
        BfhlRequest request = new BfhlRequest(Arrays.asList(
            "ABC", "123", "A1B2", "$", "%", "-50", "0", "xyz", "", null, "999", "Test99", "&"
        ));
        
        BfhlResponse response = bfhlService.processData(request, "REQ-1005");
        
        assertTrue(response.isSuccess());
        assertNotNull(response.alphabetFrequency());
        assertNotNull(response.sortedNumbers());
        assertNotNull(response.vowelCount());
        assertTrue(response.vowelCount() > 0);
        assertNotNull(response.consonantCount());
        assertNotNull(response.longestAlphabeticValue());
        assertNotNull(response.shortestAlphabeticValue());
        assertNotNull(response.summary());
        assertEquals(13, response.summary().getTotalElementsReceived());
    }
    
    @Test
    @DisplayName("Test empty data array")
    void testEmptyDataArray() {
        BfhlRequest request = new BfhlRequest(Arrays.asList());
        
        BfhlResponse response = bfhlService.processData(request, "REQ-EMPTY");
        
        assertTrue(response.isSuccess());
        assertEquals(0, response.alphabetCount());
        assertEquals(0, response.numberCount());
        assertEquals(0, response.specialCharacterCount());
        assertEquals("0", response.sum());
    }
    
    @Test
    @DisplayName("Test null and whitespace filtering")
    void testNullAndWhitespaceFiltering() {
        BfhlRequest request = new BfhlRequest(Arrays.asList(null, "", "   ", "A", "1"));
        
        BfhlResponse response = bfhlService.processData(request, "REQ-NULL");
        
        assertTrue(response.isSuccess());
        assertEquals(2, response.uniqueElementCount());
        assertEquals(5, response.summary().getTotalElementsReceived());
        assertEquals(2, response.summary().getValidElementsProcessed());
        assertEquals(3, response.summary().getInvalidElementsIgnored());
    }
    
    @Test
    @DisplayName("Test alphabet frequency counting")
    void testAlphabetFrequency() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "A", "A", "B", "B", "C"));
        
        BfhlResponse response = bfhlService.processData(request, "REQ-FREQ");
        
        assertTrue(response.containsDuplicates());
        assertNotNull(response.alphabetFrequency());
        assertEquals(3, response.alphabetFrequency().get("A"));
        assertEquals(2, response.alphabetFrequency().get("B"));
        assertEquals(1, response.alphabetFrequency().get("C"));
    }
    
    @Test
    @DisplayName("Test vowel and consonant counting")
    void testVowelConsonantCount() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "E", "I", "O", "U", "B", "C", "D"));
        
        BfhlResponse response = bfhlService.processData(request, "REQ-VOWEL");
        
        assertEquals(5, response.vowelCount());
        assertEquals(3, response.consonantCount());
    }
    
    @Test
    @DisplayName("Test longest and shortest alphabetic values")
    void testLongestShortestAlphabetic() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "AB", "ABC", "ABCD"));
        
        BfhlResponse response = bfhlService.processData(request, "REQ-LENGTH");
        
        assertEquals("ABCD", response.longestAlphabeticValue());
        assertEquals("A", response.shortestAlphabeticValue());
    }
    
    @Test
    @DisplayName("Test sorted numbers")
    void testSortedNumbers() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("50", "10", "30", "20", "40"));
        
        BfhlResponse response = bfhlService.processData(request, "REQ-SORT");
        
        assertNotNull(response.sortedNumbers());
        assertEquals(Arrays.asList(10.0, 20.0, 30.0, 40.0, 50.0), response.sortedNumbers());
    }
    
    @Test
    @DisplayName("Test processing time is recorded")
    void testProcessingTime() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "1", "B", "2"));
        
        BfhlResponse response = bfhlService.processData(request, "REQ-TIME");
        
        assertTrue(response.processingTimeMs() >= 0);
    }
    
    @Test
    @DisplayName("Test special characters only")
    void testSpecialCharactersOnly() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("$", "#", "@", "%", "&"));
        
        BfhlResponse response = bfhlService.processData(request, "REQ-SPECIAL");
        
        assertEquals(5, response.specialCharacterCount());
        assertEquals(0, response.alphabetCount());
        assertEquals(0, response.numberCount());
    }
    
    @Test
    @DisplayName("Test mixed case alphabets are uppercase in output")
    void testUppercaseConversion() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", "b", "C", "D"));
        
        BfhlResponse response = bfhlService.processData(request, "REQ-CASE");
        
        assertTrue(response.alphabets().containsAll(Arrays.asList("A", "B", "C", "D")));
    }
    
    @Test
    @DisplayName("Test zero handling")
    void testZeroHandling() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("0", "00", "000"));
        
        BfhlResponse response = bfhlService.processData(request, "REQ-ZERO");
        
        assertTrue(response.containsDuplicates());
        assertTrue(response.evenNumbers().contains("0"));
        assertEquals("0", response.sum());
    }
}
