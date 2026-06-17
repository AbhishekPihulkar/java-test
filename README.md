# BFHL API

A RESTful API service for processing and analyzing mixed data arrays containing numbers, alphabets, and special characters.

## Overview

This Spring Boot application provides endpoints to process arrays of mixed data types and return comprehensive analysis including categorization, statistical information, and data insights.

## Technology Stack

- **Java** 21
- **Spring Boot** 4.1.0
- **Maven** 3.x
- **Lombok** - Reduces boilerplate code
- **Jakarta Validation** - Input validation

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

### Building the Project

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

### Running with Docker

```bash
# Build Docker image
docker build -t bfhl-api .

# Run container
docker run -p 8080:8080 bfhl-api
```

## API Endpoints

### POST /bfhl

Processes a mixed array of data and returns categorized analysis.

**Request:**
```http
POST /bfhl
Content-Type: application/json
X-Request-Id: REQ-12345 (optional)

{
  "data": ["A", "1", "3", "z", "!", "5", "B", "@", "2"]
}
```

**Response:**
```json
{
  "is_success": true,
  "request_id": "REQ-12345",
  "odd_numbers": ["1", "3", "5"],
  "even_numbers": ["2"],
  "alphabets": ["A", "z", "B"],
  "special_characters": ["!", "@"],
  "sum": "11",
  "largest_number": "5",
  "smallest_number": "1",
  "alphabet_count": 3,
  "number_count": 4,
  "special_character_count": 2,
  "contains_duplicates": false,
  "unique_element_count": 9,
  "processing_time_ms": 12,
  "alphabet_frequency": {
    "A": 1,
    "z": 1,
    "B": 1
  },
  "sorted_numbers": [1.0, 2.0, 3.0, 5.0],
  "vowel_count": 1,
  "consonant_count": 2,
  "longest_alphabetic_value": "z",
  "shortest_alphabetic_value": "A",
  "summary": {
    "total_elements": 9,
    "numeric_percentage": 44.44,
    "alphabetic_percentage": 33.33,
    "special_character_percentage": 22.22,
    "has_numbers": true,
    "has_alphabets": true,
    "has_special_characters": true
  }
}
```

### GET /bfhl

Returns the operation code for the API.

**Request:**
```http
GET /bfhl
```

**Response:**
```json
{
  "operation_code": "BFHL_API_V1"
}
```

### GET /bfhl/health

Health check endpoint for monitoring service status.

**Request:**
```http
GET /bfhl/health
```

**Response:**
```json
{
  "status": "UP",
  "service": "BFHL API"
}
```

## Response Fields

| Field | Type | Description |
|-------|------|-------------|
| is_success | boolean | Indicates if the request was processed successfully |
| request_id | string | Unique identifier for the request |
| odd_numbers | array | List of odd numbers from input |
| even_numbers | array | List of even numbers from input |
| alphabets | array | List of alphabetic characters |
| special_characters | array | List of special characters |
| sum | string | Sum of all numeric values |
| largest_number | string | Largest number in the input |
| smallest_number | string | Smallest number in the input |
| alphabet_count | integer | Total count of alphabets |
| number_count | integer | Total count of numbers |
| special_character_count | integer | Total count of special characters |
| contains_duplicates | boolean | Whether duplicates exist in input |
| unique_element_count | integer | Count of unique elements |
| processing_time_ms | long | Time taken to process the request |
| alphabet_frequency | object | Frequency map of each alphabet |
| sorted_numbers | array | Numbers sorted in ascending order |
| vowel_count | integer | Count of vowel characters |
| consonant_count | integer | Count of consonant characters |
| longest_alphabetic_value | string | Alphabet with highest ASCII value |
| shortest_alphabetic_value | string | Alphabet with lowest ASCII value |
| summary | object | Summary statistics of the data |

## Examples

### Example 1: Basic Mixed Data

**Request:**
```json
{
  "data": ["1", "2", "A", "B"]
}
```

**Key Response Fields:**
```json
{
  "is_success": true,
  "odd_numbers": ["1"],
  "even_numbers": ["2"],
  "alphabets": ["A", "B"],
  "sum": "3",
  "vowel_count": 1,
  "consonant_count": 1
}
```

### Example 2: Data with Special Characters

**Request:**
```json
{
  "data": ["10", "a", "5", "Z", "#", "20", "b", "$", "15", "c"]
}
```

**Key Response Fields:**
```json
{
  "is_success": true,
  "odd_numbers": ["5", "15"],
  "even_numbers": ["10", "20"],
  "alphabets": ["a", "Z", "b", "c"],
  "special_characters": ["#", "$"],
  "sum": "50",
  "largest_number": "20",
  "contains_duplicates": false
}
```

### Example 3: Duplicate Detection

**Request:**
```json
{
  "data": ["A", "1", "A", "2", "1", "!"]
}
```

**Key Response Fields:**
```json
{
  "is_success": true,
  "contains_duplicates": true,
  "unique_element_count": 4,
  "alphabet_frequency": {
    "A": 2
  },
  "sum": "4"
}
```

## Error Handling

The API handles errors gracefully and returns appropriate HTTP status codes:

**400 Bad Request** - Invalid input or validation error
```json
{
  "timestamp": "2026-06-17T10:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Data array cannot be null",
  "path": "/bfhl"
}
```

**500 Internal Server Error** - Server-side processing error
```json
{
  "timestamp": "2026-06-17T10:30:00.000+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An error occurred while processing the request",
  "path": "/bfhl"
}
```

## Testing

Run the test suite:

```bash
# Run all tests
./mvnw test

# Run tests with coverage report
./mvnw clean test jacoco:report

# View coverage report (Windows)
start target/site/jacoco/index.html
```

## Sample Requests

### Using cURL

```bash
# POST request with data
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -H "X-Request-Id: REQ-TEST-001" \
  -d '{"data": ["A", "1", "3", "z", "!", "5"]}'

# GET operation code
curl http://localhost:8080/bfhl

# Health check
curl http://localhost:8080/bfhl/health
```

### Using PowerShell

```powershell
# POST request
$body = @{
    data = @("A", "1", "3", "z", "!", "5")
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/bfhl" `
  -Method Post `
  -ContentType "application/json" `
  -Headers @{"X-Request-Id"="REQ-TEST-001"} `
  -Body $body

# GET request
Invoke-RestMethod -Uri "http://localhost:8080/bfhl" -Method Get
```

## Project Structure

```
src/
├── main/
│   ├── java/com/javaTest/java/
│   │   ├── controller/          # REST API controllers
│   │   │   └── BfhlController.java
│   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── BfhlRequest.java
│   │   │   ├── BfhlResponse.java
│   │   │   ├── ErrorResponse.java
│   │   │   └── SummaryDto.java
│   │   ├── exception/           # Global exception handling
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── service/             # Business logic
│   │   │   ├── BfhlService.java
│   │   │   └── BfhlServiceImpl.java
│   │   └── JavaApplication.java # Main application class
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/javaTest/java/
        ├── controller/          # Controller integration tests
        └── service/             # Service unit tests
```

## Configuration

Configuration can be modified in `src/main/resources/application.properties`:

```properties
server.port=8080
spring.application.name=BFHL API
```

## Features

- ✅ Data categorization (numbers, alphabets, special characters)
- ✅ Numeric analysis (sum, min, max, odd/even separation)
- ✅ Alphabetic analysis (vowels, consonants, frequency)
- ✅ Duplicate detection
- ✅ Statistical summaries
- ✅ Performance metrics
- ✅ Request tracking with custom headers
- ✅ Comprehensive error handling
- ✅ Input validation
- ✅ RESTful API design
- ✅ Health monitoring endpoints
- ✅ Docker support

## License

This project is developed for educational purposes.

## Support

For issues or questions, please refer to the project documentation or contact the development team.
