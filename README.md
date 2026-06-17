# BFHL API

D.Y.Patil Campus Hiring API Round - A Spring Boot REST API for processing mixed data arrays.

## 🚀 Overview

This API processes mixed arrays containing numbers, alphabets, and special characters, providing comprehensive analysis and categorization of the input data.

## 📋 Features

- ✅ Data categorization (numbers, alphabets, special characters)
- ✅ Number analysis (odd/even, sum, largest/smallest, sorting)
- ✅ Alphabet analysis (vowels, consonants, frequency, longest/shortest)
- ✅ Duplicate detection
- ✅ Performance metrics
- ✅ Request tracking with X-Request-Id header
- ✅ Health check endpoints

## 🛠️ Tech Stack

- **Java**: 21
- **Spring Boot**: 4.1.0
- **Maven**: 3.x
- **Lombok**: For reducing boilerplate code
- **Jakarta Validation**: For request validation

## 📦 Installation

### Prerequisites
- Java 21 or higher
- Maven 3.6+

### Build and Run

```bash
# Clone the repository
git clone <repository-url>
cd java

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## 🔌 API Endpoints

### 1. POST /bfhl - Process Data

**Request:**
```json
POST http://localhost:8080/bfhl
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

### 2. GET /bfhl - Operation Code Check

**Request:**
```bash
GET http://localhost:8080/bfhl
```

**Response:**
```json
{
  "operation_code": "BFHL_API_V1"
}
```

### 3. GET /bfhl/health - Health Check

**Request:**
```bash
GET http://localhost:8080/bfhl/health
```

**Response:**
```json
{
  "status": "UP",
  "service": "BFHL API"
}
```

## 📊 Sample Output Images

### Example 1: Basic Input
**Input:**
```json
{
  "data": ["1", "2", "A", "B"]
}
```

**Output:**
```json
{
  "is_success": true,
  "request_id": "REQ-1718640000123",
  "odd_numbers": ["1"],
  "even_numbers": ["2"],
  "alphabets": ["A", "B"],
  "special_characters": [],
  "sum": "3",
  "largest_number": "2",
  "smallest_number": "1",
  "alphabet_count": 2,
  "number_count": 2,
  "special_character_count": 0,
  "contains_duplicates": false,
  "unique_element_count": 4,
  "processing_time_ms": 8,
  "alphabet_frequency": {
    "A": 1,
    "B": 1
  },
  "sorted_numbers": [1.0, 2.0],
  "vowel_count": 1,
  "consonant_count": 1,
  "longest_alphabetic_value": "B",
  "shortest_alphabetic_value": "A",
  "summary": {
    "total_elements": 4,
    "numeric_percentage": 50.0,
    "alphabetic_percentage": 50.0,
    "special_character_percentage": 0.0,
    "has_numbers": true,
    "has_alphabets": true,
    "has_special_characters": false
  }
}
```

### Example 2: Complex Mixed Input
**Input:**
```json
{
  "data": ["10", "a", "5", "Z", "#", "20", "b", "$", "15", "c"]
}
```

**Output:**
```json
{
  "is_success": true,
  "request_id": "REQ-1718640001456",
  "odd_numbers": ["5", "15"],
  "even_numbers": ["10", "20"],
  "alphabets": ["a", "Z", "b", "c"],
  "special_characters": ["#", "$"],
  "sum": "50",
  "largest_number": "20",
  "smallest_number": "5",
  "alphabet_count": 4,
  "number_count": 4,
  "special_character_count": 2,
  "contains_duplicates": false,
  "unique_element_count": 10,
  "processing_time_ms": 15,
  "alphabet_frequency": {
    "a": 1,
    "Z": 1,
    "b": 1,
    "c": 1
  },
  "sorted_numbers": [5.0, 10.0, 15.0, 20.0],
  "vowel_count": 1,
  "consonant_count": 3,
  "longest_alphabetic_value": "c",
  "shortest_alphabetic_value": "Z",
  "summary": {
    "total_elements": 10,
    "numeric_percentage": 40.0,
    "alphabetic_percentage": 40.0,
    "special_character_percentage": 20.0,
    "has_numbers": true,
    "has_alphabets": true,
    "has_special_characters": true
  }
}
```

### Example 3: With Duplicates
**Input:**
```json
{
  "data": ["A", "1", "A", "2", "1", "!"]
}
```

**Output:**
```json
{
  "is_success": true,
  "request_id": "REQ-1718640002789",
  "odd_numbers": ["1", "1"],
  "even_numbers": ["2"],
  "alphabets": ["A", "A"],
  "special_characters": ["!"],
  "sum": "4",
  "largest_number": "2",
  "smallest_number": "1",
  "alphabet_count": 2,
  "number_count": 3,
  "special_character_count": 1,
  "contains_duplicates": true,
  "unique_element_count": 4,
  "processing_time_ms": 10,
  "alphabet_frequency": {
    "A": 2
  },
  "sorted_numbers": [1.0, 1.0, 2.0],
  "vowel_count": 2,
  "consonant_count": 0,
  "longest_alphabetic_value": "A",
  "shortest_alphabetic_value": "A",
  "summary": {
    "total_elements": 6,
    "numeric_percentage": 50.0,
    "alphabetic_percentage": 33.33,
    "special_character_percentage": 16.67,
    "has_numbers": true,
    "has_alphabets": true,
    "has_special_characters": true
  }
}
```

## 🧪 Testing

Run unit and integration tests:

```bash
# Run all tests
./mvnw test

# Run with coverage report
./mvnw clean test jacoco:report

# View coverage report
open target/site/jacoco/index.html
```

## 🐳 Docker

### Build Docker Image
```bash
docker build -t bfhl-api .
```

### Run Container
```bash
docker run -p 8080:8080 bfhl-api
```

## 📝 Sample Requests

### Using cURL
```bash
# POST request
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -H "X-Request-Id: REQ-TEST-001" \
  -d '{"data": ["A", "1", "3", "z", "!", "5"]}'

# GET operation code
curl http://localhost:8080/bfhl

# Health check
curl http://localhost:8080/bfhl/health
```

### Using PowerShell (Windows)
See `sample-requests.ps1` file for detailed examples.

### Using Bash (Linux/Mac)
See `sample-requests.sh` file for detailed examples.

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/javaTest/java/
│   │   ├── controller/          # REST controllers
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── exception/           # Exception handlers
│   │   ├── service/             # Business logic
│   │   └── JavaApplication.java # Main application
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/javaTest/java/
        ├── controller/          # Controller tests
        └── service/             # Service tests
```

## 🔧 Configuration

Application properties (`src/main/resources/application.properties`):
```properties
server.port=8080
spring.application.name=BFHL API
```

## 📄 Error Handling

The API includes global exception handling for:
- Validation errors (400 Bad Request)
- Missing required fields
- Invalid data formats
- Internal server errors (500)

**Error Response Example:**
```json
{
  "timestamp": "2026-06-17T10:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Data array cannot be null",
  "path": "/bfhl"
}
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📜 License

This project is part of D.Y.Patil Campus Hiring API Round.

## 👨‍💻 Author

Campus Hiring Round Submission

## 📞 Support

For issues or questions, please create an issue in the repository.

---

**Happy Coding! 🚀**
