# BFHL API - Sample Test Requests (PowerShell)
# Make sure the application is running on http://localhost:8080

$BaseUrl = "http://localhost:8080/bfhl"

Write-Host "======================================" -ForegroundColor Cyan
Write-Host "BFHL API - Sample Test Requests" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan
Write-Host ""

# Health Check
Write-Host "1. Health Check (GET /bfhl)" -ForegroundColor Yellow
Write-Host "--------------------------------------"
try {
    $response = Invoke-RestMethod -Uri $BaseUrl -Method Get
    $response | ConvertTo-Json
} catch {
    Write-Host "Error: $_" -ForegroundColor Red
}
Write-Host "`n"

# Example 1: Basic Input
Write-Host "2. Example 1: Basic Input" -ForegroundColor Yellow
Write-Host "--------------------------------------"
$headers = @{
    "Content-Type" = "application/json"
    "X-Request-Id" = "REQ-1001"
}
$body = @{
    data = @("A", "1", "22", "$", "B", "7")
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri $BaseUrl -Method Post -Headers $headers -Body $body
    $response | ConvertTo-Json -Depth 10
} catch {
    Write-Host "Error: $_" -ForegroundColor Red
}
Write-Host "`n"

# Example 2: Alphanumeric Strings
Write-Host "3. Example 2: Alphanumeric Strings" -ForegroundColor Yellow
Write-Host "--------------------------------------"
$headers = @{
    "Content-Type" = "application/json"
    "X-Request-Id" = "REQ-1002"
}
$body = @{
    data = @("A1B2", "100", "#", "Test123", "Z", "55")
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri $BaseUrl -Method Post -Headers $headers -Body $body
    $response | ConvertTo-Json -Depth 10
} catch {
    Write-Host "Error: $_" -ForegroundColor Red
}
Write-Host "`n"

# Example 3: Duplicates and Null Values
Write-Host "4. Example 3: Duplicates and Null Values" -ForegroundColor Yellow
Write-Host "--------------------------------------"
$headers = @{
    "Content-Type" = "application/json"
    "X-Request-Id" = "REQ-1003"
}
$body = @{
    data = @("10", "10", "A", "A", "", $null, "&", "5")
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri $BaseUrl -Method Post -Headers $headers -Body $body
    $response | ConvertTo-Json -Depth 10
} catch {
    Write-Host "Error: $_" -ForegroundColor Red
}
Write-Host "`n"

# Example 4: Negative and Decimal Numbers
Write-Host "5. Example 4: Negative and Decimal Numbers" -ForegroundColor Yellow
Write-Host "--------------------------------------"
$headers = @{
    "Content-Type" = "application/json"
    "X-Request-Id" = "REQ-1004"
}
$body = @{
    data = @("-10", "25.5", "-100.75", "B", "@", "5", "A9")
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri $BaseUrl -Method Post -Headers $headers -Body $body
    $response | ConvertTo-Json -Depth 10
} catch {
    Write-Host "Error: $_" -ForegroundColor Red
}
Write-Host "`n"

# Example 5: Comprehensive Test
Write-Host "6. Example 5: Comprehensive Test" -ForegroundColor Yellow
Write-Host "--------------------------------------"
$headers = @{
    "Content-Type" = "application/json"
    "X-Request-Id" = "REQ-1005"
}
$body = @{
    data = @("ABC", "123", "A1B2", "$", "%", "-50", "0", "xyz", "", $null, "999", "Test99", "&")
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri $BaseUrl -Method Post -Headers $headers -Body $body
    $response | ConvertTo-Json -Depth 10
} catch {
    Write-Host "Error: $_" -ForegroundColor Red
}
Write-Host "`n"

Write-Host "======================================" -ForegroundColor Cyan
Write-Host "All tests completed!" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan
