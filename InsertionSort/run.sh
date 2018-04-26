#!/bin/bash

echo "Compiling program"
javac assignment1/Main.java
echo "Compiling Complete"
echo "Running Program"
java assignment1/Main
echo "End run"
rm assignment1/*.class
