#!/bin/bash

echo "=========================================="
echo "Compiling Blood & Organ Donation System"
echo "=========================================="

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile all Java files
echo "Compiling source files..."
javac -d bin -cp "lib/*" src/models/*.java src/database/*.java src/interfaces/*.java src/utils/*.java src/gui/*.java src/MainApplication.java

if [ $? -eq 0 ]; then
    echo ""
    echo "=========================================="
    echo "Compilation successful!"
    echo "=========================================="
    echo ""
    echo "To run the application, execute: ./run.sh"
    echo ""
else
    echo ""
    echo "=========================================="
    echo "Compilation failed!"
    echo "=========================================="
    echo "Please check the error messages above."
    echo ""
fi
