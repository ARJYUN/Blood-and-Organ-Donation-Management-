@echo off
echo Blood Donation Management System - Database Setup
echo ================================================
echo.
echo This script will help you set up the MySQL database.
echo.
echo Prerequisites:
echo 1. MySQL Server must be installed and running
echo 2. MySQL command line tools must be available
echo.
echo Please enter your MySQL root password when prompted.
echo (If you haven't set a password, just press Enter)
echo.
pause
echo.
echo Setting up database...
echo.
mysql -u root -p < setup_database.sql
echo.
if %ERRORLEVEL% EQU 0 (
    echo Database setup completed successfully!
    echo.
    echo You can now run the application using:
    echo java -jar target/blood-organ-donation-management-1.0.0.jar
    echo.
) else (
    echo Database setup failed!
    echo Please check your MySQL installation and credentials.
    echo.
    echo Manual setup:
    echo 1. Open MySQL command line: mysql -u root -p
    echo 2. Run: source setup_database.sql;
    echo.
)
pause
