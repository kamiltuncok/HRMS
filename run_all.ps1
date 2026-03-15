# Stop processes on port 8080 (Backend)
$port8080 = Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue
if ($port8080) {
    Write-Host "Stopping processes on port 8080..."
    $port8080 | ForEach-Object { Stop-Process -Id $_.OwningProcess -Force }
}

# Start Backend
Write-Host "Starting Backend..."
cd "c:\Users\MONSTER\OneDrive\Belgeler\GitHub\HRMS"
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
# Start in background using Start-Process
Start-Process -FilePath "powershell.exe" -ArgumentList "-NoProfile", "-Command", "cd 'c:\Users\MONSTER\OneDrive\Belgeler\GitHub\HRMS'; `$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21'; .\mvnw.cmd spring-boot:run" -WindowStyle Hidden

# Start Frontend
Write-Host "Starting Frontend..."
cd "c:\Users\MONSTER\OneDrive\Belgeler\GitHub\hrms-frontend"
Start-Process -FilePath "powershell.exe" -ArgumentList "-NoProfile", "-Command", "cd 'c:\Users\MONSTER\OneDrive\Belgeler\GitHub\hrms-frontend'; npm run dev" -WindowStyle Hidden

Write-Host "All systems starting. Backend on 8080, Frontend on 5173."
