@echo off
echo Blood and Organ Donation Management System
echo ==========================================
echo.
echo IMPORTANT: Database setup required before running!
echo.
echo Quick Database Setup:
echo 1. Run: setup_database.bat (recommended)
echo 2. Or manually: mysql -u root -p < setup_database.sql
echo.
echo Then update database credentials in:
echo src/main/resources/database.properties
echo.
echo Default Login Credentials:
echo Admin: admin@blooddonation.com / password123
echo Donor: john.smith@email.com / password123
echo Receiver: mike.wilson@email.com / password123
echo Charity: redcross@charity.org / password123
echo.
echo Press any key to start the application...
pause
echo.
echo Starting application...
java -jar target/blood-organ-donation-management-1.0.0.jar
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Application failed to start!
    echo Please check:
    echo 1. MySQL is running
    echo 2. Database is created (run setup_database.bat)
    echo 3. Database credentials are correct in database.properties
    echo.
)
pause
