#!/usr/bin/bash

echo "🎅 Welcome to the advent of code 2024 solver 🎄"
read -p "Input: [e.g. 1-5,24,25] days / [0] run all / [test] to run tests: " input

if [[ $input == "test" ]]; then
    echo "🎉 Running tests 🎈"
    ./gradlew test
    exit 0
elif [[ $input == "0" ]]; then
    echo "🎉 Running all days 🎈"
    ./gradlew run --args=" " -q
    exit 0
else
    echo "🎉 Running days $input 🎈"
    ./gradlew run --args="-days $input" -q
fi

