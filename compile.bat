@echo off
echo ==========================================
echo Compiling Blood & Organ Donation System
echo ==========================================

REM Create bin directory if it doesn't exist
if not exist "bin" mkdir bin

REM Compile all Java files
echo Compiling source files...
javac -d bin -cp "lib/*" src/models/*.java src/database/*.java src/interfaces/*.java src/utils/*.java src/gui/*.java src/MainApplication.java

if %errorlevel% equ 0 (
    echo.
    echo ==========================================
    echo Compilation successful!
    echo ==========================================
    echo.
    echo To run the application, execute: run.bat
    echo.
) else (
    echo.
    echo ==========================================
    echo Compilation failed!
    echo ==========================================
    echo Please check the error messages above.
    echo.
)

pause
