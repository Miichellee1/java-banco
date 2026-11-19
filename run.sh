#!/usr/bin/env bash
set -e
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -Dfile.encoding=UTF-8 -cp out banco.Main
