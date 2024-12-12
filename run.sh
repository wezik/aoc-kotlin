#!/usr/bin/bash

echo "🎅 Welcome to the advent of code 2024 solver 🎄"
read -p "Input: [1-25] day / [0] run all / [test] to run tests: " input

if [[ $input == "test" ]]; then
    echo "🎉 Running tests 🎈"
    ./gradlew test
    exit 0
elif [ $input -eq 0 ]; then
    echo "🎉 Running all days 🎈"
    ./gradlew run --args="-runAll true"
    exit 0
else
    echo "🎉 Running day $input 🎈"
    ./gradlew run --args="-day $input"
fi

