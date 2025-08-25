#!/bin/bash

# Legend of Java Build Script

echo "Building Legend of Java..."

# Create build directory if it doesn't exist
mkdir -p build

# Compile all Java files and place .class files in build directory
javac -d build -cp src src/Main/Main.java

# Copy resources to build directory
cp -r res build/

echo "Build complete! Run with: java -cp build Main.Main"
