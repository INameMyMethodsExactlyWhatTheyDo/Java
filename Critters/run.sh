#!/bin/sh

echo "run"
cd src
javac assignment4/*.java
java assignment4.Main
cd ..
rm src/assignment4/*.class
