# 📸 Screenshots Guide for README

This guide will help you capture and add output screenshots to the README.md file.

## 🎯 Required Screenshots

You need to capture **6 screenshots** for the following API calls:

### 1️⃣ POST /bfhl - Basic Input (`post-basic-input.png` & `post-basic-output.png`)

**Request:**
```http
POST http://localhost:8080/bfhl
Content-Type: application/json

{
  "data": ["1", "2", "A", "B"]
}
```

**Expected Output Preview:**
- Should show odd_numbers: ["1"]
- Should show even_numbers: ["2"]
- Should show alphabets: ["A", "B"]
- Should show vowel_count: 1, consonant_count: 1

---

### 2️⃣ POST /bfhl - Complex Input (`post-complex-input.png` & `post-complex-output.png`)

**Request:**
```http
POST http://localhost:8080/bfhl
Content-Type: application/json
X-Request-Id: REQ-COMPLEX-001

{
  "data": ["10", "a", "5", "Z", "#", "20", "b", "$", "15", "c"]
}
```

**Expected Output Preview:**
- Should show sum: "50"
- Should show largest_number: "20"
- Should show special_characters: ["#", "$"]
- Should show alphabet_frequency with all letters

---

### 3️⃣ POST /bfhl - Duplicates (`post-duplicates-input.png` & `post-duplicates-output.png`)

**Request:**
```http
POST http://localhost:8080/bfhl
Content-Type: application/json

{
  "data": ["A", "1", "A", "2", "1", "!"]
}
```

**Expected Output Preview:**
- Should show contains_duplicates: true
- Should show alphabet_frequency: {"A": 2}
- Should show duplicate numbers in odd_numbers

---

### 4️⃣ GET /bfhl - Operation Code (`get-operation-code.png`)

**Request:**
```http
GET http://localhost:8080/bfhl
```

**Expected Output Preview:**
```json
{
  "operation_code": "BFHL_API_V1"
}
```

---

### 5️⃣ GET /bfhl/health - Health Check (`get-health-check.png`)

**Request:**
```http
GET http://localhost:8080/bfhl/health
```

**Expected Output Preview:**
```json
{
  "status": "UP",
  "service": "BFHL API"
}
```

---

### 6️⃣ Error Response - Validation (`error-validation.png`)

**Request (intentionally invalid):**
```http
POST http://localhost:8080/bfhl
Content-Type: application/json

{
  "invalid": []
}
```

**Expected Output Preview:**
- Should show status: 400
- Should show error message about missing "data" field

---

## 🛠️ How to Capture Screenshots

### Option 1: Using Postman
1. Start your Spring Boot application: `./mvnw spring-boot:run`
2. Open Postman
3. Create requests for each endpoint
4. Send the request
5. Take a screenshot of both the request and response
6. Save with the appropriate filename

### Option 2: Using VS Code Thunder Client
1. Install Thunder Client extension
2. Create new requests for each endpoint
3. Send requests and capture screenshots
4. Save in `docs/images/` folder

### Option 3: Using cURL + Terminal
1. Run the cURL command
2. Take screenshot of the terminal output
3. Save the image

### Option 4: Using Browser + Fetch API
1. Open browser developer console (F12)
2. Run fetch commands:
```javascript
// POST Example
fetch('http://localhost:8080/bfhl', {
  method: 'POST',
  headers: {'Content-Type': 'application/json'},
  body: JSON.stringify({data: ["1", "2", "A", "B"]})
})
.then(r => r.json())
.then(console.log);

// GET Example
fetch('http://localhost:8080/bfhl')
.then(r => r.json())
.then(console.log);
```
3. Take screenshot of console output
4. Save the images

---

## 📁 File Organization

Create this directory structure:

```
project-root/
├── docs/
│   └── images/
│       ├── post-basic-input.png
│       ├── post-basic-output.png
│       ├── post-complex-input.png
│       ├── post-complex-output.png
│       ├── post-duplicates-input.png
│       ├── post-duplicates-output.png
│       ├── get-operation-code.png
│       ├── get-health-check.png
│       └── error-validation.png
└── README.md
```

## ✅ Checklist

- [ ] Application is running on http://localhost:8080
- [ ] Created `docs/images/` folder
- [ ] Captured POST basic input screenshot
- [ ] Captured POST basic output screenshot
- [ ] Captured POST complex input screenshot
- [ ] Captured POST complex output screenshot
- [ ] Captured POST duplicates input screenshot
- [ ] Captured POST duplicates output screenshot
- [ ] Captured GET operation code screenshot
- [ ] Captured GET health check screenshot
- [ ] Captured error validation screenshot
- [ ] All images are saved in `docs/images/` with correct names
- [ ] Verified images appear in README.md

## 💡 Tips

1. **Image Format**: Use PNG for best quality
2. **Resolution**: Use at least 1080p for clarity
3. **Cropping**: Crop unnecessary parts (browser bars, desktop background)
4. **Highlighting**: You can add arrows or highlights to important fields
5. **Naming**: Use exact filenames as specified above

## 🖼️ Alternative: Use Code Blocks with Syntax Highlighting

If you prefer not to use images, you can keep the formatted JSON code blocks with syntax highlighting that are already in the README. They provide:
- ✅ Copy-paste friendly
- ✅ Search-friendly
- ✅ Diff-friendly
- ✅ Works in dark/light mode

---

**Once you've added the screenshots, they'll automatically appear in the README.md!** 🎉
