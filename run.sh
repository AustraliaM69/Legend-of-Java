#!/bin/bash

# Legend of Java Run Script

echo "Running Legend of Java..."

# Check if build directory exists and has .class files
if [ ! -d "build" ] || [ ! -f "build/Main/Main.class" ]; then
    echo "Build not found. Building first..."
    ./build.sh
fi

# Run the game
java -cp build Main.Main
