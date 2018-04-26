#!/bin/sh

javac assignment3/Main.java assignment3/UndirectedGraph.java
java assignment3/Main input.txt log.txt
rm assignment3/*.class

