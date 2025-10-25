#!/bin/bash

echo "=========================================="
echo "Running Blood & Organ Donation System"
echo "=========================================="
echo ""

# Check if bin directory exists
if [ ! -d "bin" ]; then
    echo "Error: bin directory not found!"
    echo "Please compile the project first using ./compile.sh"
    echo ""
    exit 1
fi

# Run the application
java -cp "bin:lib/*" MainApplication
