#!/bin/bash

# BFHL API - Sample Test Requests
# Make sure the application is running on http://localhost:8080

BASE_URL="http://localhost:8080/bfhl"

echo "======================================"
echo "BFHL API - Sample Test Requests"
echo "======================================"
echo ""

# Health Check
echo "1. Health Check (GET /bfhl)"
echo "--------------------------------------"
curl -X GET "$BASE_URL"
echo -e "\n\n"

# Example 1: Basic Input
echo "2. Example 1: Basic Input"
echo "--------------------------------------"
curl -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -H "X-Request-Id: REQ-1001" \
  -d '{
    "data": ["A", "1", "22", "$", "B", "7"]
  }'
echo -e "\n\n"

# Example 2: Alphanumeric Strings
echo "3. Example 2: Alphanumeric Strings"
echo "--------------------------------------"
curl -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -H "X-Request-Id: REQ-1002" \
  -d '{
    "data": ["A1B2", "100", "#", "Test123", "Z", "55"]
  }'
echo -e "\n\n"

# Example 3: Duplicates and Null Values
echo "4. Example 3: Duplicates and Null Values"
echo "--------------------------------------"
curl -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -H "X-Request-Id: REQ-1003" \
  -d '{
    "data": ["10", "10", "A", "A", "", null, "&", "5"]
  }'
echo -e "\n\n"

# Example 4: Negative and Decimal Numbers
echo "5. Example 4: Negative and Decimal Numbers"
echo "--------------------------------------"
curl -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -H "X-Request-Id: REQ-1004" \
  -d '{
    "data": ["-10", "25.5", "-100.75", "B", "@", "5", "A9"]
  }'
echo -e "\n\n"

# Example 5: Comprehensive Test
echo "6. Example 5: Comprehensive Test"
echo "--------------------------------------"
curl -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -H "X-Request-Id: REQ-1005" \
  -d '{
    "data": ["ABC", "123", "A1B2", "$", "%", "-50", "0", "xyz", "", null, "999", "Test99", "&"]
  }'
echo -e "\n\n"

echo "======================================"
echo "All tests completed!"
echo "======================================"
