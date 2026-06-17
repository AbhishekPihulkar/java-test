package com.javaTest.java.service;

import com.javaTest.java.dto.BfhlRequest;
import com.javaTest.java.dto.BfhlResponse;
import com.javaTest.java.dto.SummaryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BfhlServiceImpl implements BfhlService {
    
    private static final Set<Character> VOWELS = Set.of('A', 'E', 'I', 'O', 'U', 'a', 'e', 'i', 'o', 'u');
    
    @Override
    public BfhlResponse processData(BfhlRequest request, String requestId) {
        long startTime = System.currentTimeMillis();
        
        log.info("Processing request with ID: {}", requestId);
        
        List<String> inputData = request.getData();
        int totalElementsReceived = inputData == null ? 0 : inputData.size();
        
        // Remove null, empty, and whitespace-only strings
        List<String> validData = inputData == null ? new ArrayList<>() : 
                inputData.stream()
                        .filter(Objects::nonNull)
                        .filter(s -> !s.trim().isEmpty())
                        .collect(Collectors.toList());
        
        // Check for duplicates before removing them
        boolean containsDuplicates = validData.size() != new HashSet<>(validData).size();
        
        // Remove duplicates
        List<String> uniqueData = new ArrayList<>(new LinkedHashSet<>(validData));
        int uniqueElementCount = uniqueData.size();
        
        // Process data
        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        List<Double> numbers = new ArrayList<>();
        Map<String, Integer> alphabetFrequency = new TreeMap<>();
        List<String> pureAlphabeticStrings = new ArrayList<>();
        
        for (String element : uniqueData) {
            processElement(element, oddNumbers, evenNumbers, alphabets, specialCharacters, 
                    numbers, alphabetFrequency, pureAlphabeticStrings);
        }
        
        // Calculate statistics
        String sum = calculateSum(numbers);
        String largestNumber = numbers.isEmpty() ? null : String.valueOf(Collections.max(numbers));
        String smallestNumber = numbers.isEmpty() ? null : String.valueOf(Collections.min(numbers));
        
        int alphabetCount = alphabets.size();
        int numberCount = numbers.size();
        int specialCharacterCount = specialCharacters.size();
        
        List<Double> sortedNumbers = new ArrayList<>(numbers);
        Collections.sort(sortedNumbers);
        
        int vowelCount = countVowels(alphabets);
        int consonantCount = alphabetCount - vowelCount;
        
        String longestAlphabetic = findLongestAlphabetic(pureAlphabeticStrings);
        String shortestAlphabetic = findShortestAlphabetic(pureAlphabeticStrings);
        
        // Summary
        int invalidElementsIgnored = totalElementsReceived - uniqueData.size();
        SummaryDto summary = SummaryDto.builder()
                .totalElementsReceived(totalElementsReceived)
                .validElementsProcessed(uniqueElementCount)
                .invalidElementsIgnored(invalidElementsIgnored)
                .build();
        
        long processingTime = System.currentTimeMillis() - startTime;
        
        log.info("Request {} processed in {}ms", requestId, processingTime);
        
        return BfhlResponse.builder()
                .isSuccess(true)
                .requestId(requestId)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialCharacters)
                .sum(sum)
                .largestNumber(largestNumber)
                .smallestNumber(smallestNumber)
                .alphabetCount(alphabetCount)
                .numberCount(numberCount)
                .specialCharacterCount(specialCharacterCount)
                .containsDuplicates(containsDuplicates)
                .uniqueElementCount(uniqueElementCount)
                .processingTimeMs(processingTime)
                .alphabetFrequency(alphabetFrequency.isEmpty() ? null : alphabetFrequency)
                .sortedNumbers(sortedNumbers.isEmpty() ? null : sortedNumbers)
                .vowelCount(vowelCount > 0 ? vowelCount : null)
                .consonantCount(consonantCount > 0 ? consonantCount : null)
                .longestAlphabeticValue(longestAlphabetic)
                .shortestAlphabeticValue(shortestAlphabetic)
                .summary(summary)
                .build();
    }
    
    private void processElement(String element, List<String> oddNumbers, List<String> evenNumbers,
                                 List<String> alphabets, List<String> specialCharacters,
                                 List<Double> numbers, Map<String, Integer> alphabetFrequency,
                                 List<String> pureAlphabeticStrings) {
        
        // Try to parse as number (including negative and decimal)
        try {
            double numValue = Double.parseDouble(element);
            numbers.add(numValue);
            
            // Check if odd or even (only for whole numbers)
            if (numValue == Math.floor(numValue)) {
                long intValue = (long) numValue;
                if (intValue % 2 == 0) {
                    evenNumbers.add(element);
                } else {
                    oddNumbers.add(element);
                }
            } else {
                // Decimal numbers don't go into odd/even
            }
            return;
        } catch (NumberFormatException e) {
            // Not a pure number, continue processing
        }
        
        // Check if pure alphabetic
        if (element.matches("[a-zA-Z]+")) {
            pureAlphabeticStrings.add(element);
            // Add each letter to alphabets
            for (char c : element.toCharArray()) {
                String upperChar = String.valueOf(Character.toUpperCase(c));
                alphabets.add(upperChar);
                alphabetFrequency.merge(upperChar, 1, Integer::sum);
            }
            return;
        }
        
        // Check if pure special character
        if (element.matches("[^a-zA-Z0-9]+")) {
            for (char c : element.toCharArray()) {
                specialCharacters.add(String.valueOf(c));
            }
            return;
        }
        
        // Alphanumeric string - extract components
        for (char c : element.toCharArray()) {
            if (Character.isDigit(c)) {
                // Extract digit
                double digitValue = Character.getNumericValue(c);
                numbers.add(digitValue);
                if (digitValue % 2 == 0) {
                    evenNumbers.add(String.valueOf(c));
                } else {
                    oddNumbers.add(String.valueOf(c));
                }
            } else if (Character.isLetter(c)) {
                String upperChar = String.valueOf(Character.toUpperCase(c));
                alphabets.add(upperChar);
                alphabetFrequency.merge(upperChar, 1, Integer::sum);
            } else {
                // Special character
                specialCharacters.add(String.valueOf(c));
            }
        }
    }
    
    private String calculateSum(List<Double> numbers) {
        if (numbers.isEmpty()) {
            return "0";
        }
        double sum = numbers.stream().mapToDouble(Double::doubleValue).sum();
        // Return as integer string if whole number, otherwise with decimals
        if (sum == Math.floor(sum)) {
            return String.valueOf((long) sum);
        }
        return String.valueOf(sum);
    }
    
    private int countVowels(List<String> alphabets) {
        return (int) alphabets.stream()
                .filter(s -> VOWELS.contains(s.charAt(0)))
                .count();
    }
    
    private String findLongestAlphabetic(List<String> alphabeticStrings) {
        return alphabeticStrings.stream()
                .max(Comparator.comparingInt(String::length))
                .map(String::toUpperCase)
                .orElse(null);
    }
    
    private String findShortestAlphabetic(List<String> alphabeticStrings) {
        return alphabeticStrings.stream()
                .min(Comparator.comparingInt(String::length))
                .map(String::toUpperCase)
                .orElse(null);
    }
}
