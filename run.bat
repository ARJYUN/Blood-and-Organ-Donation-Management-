@echo off
echo ==========================================
echo Running Blood & Organ Donation System
echo ==========================================
echo.

REM Check if bin directory exists
if not exist "bin" (
    echo Error: bin directory not found!
    echo Please compile the project first using compile.bat
    echo.
    pause
    exit /b 1
)

REM Run the application
java -cp "bin;lib/*" MainApplication

pause
