#!/usr/bin/bash

# Provide a session cookie environment variable SESSION_COOKIE
day="$1"
padded_day=$(printf "%02d" "$day")

input="inputs/Day$padded_day.txt"

if [[ "$#" -gt 1 ]]; then
  if [[ "$2" == "test" ]]; then
    if [[ "$day" -eq 0 ]]; then
      ./gradlew test --console=rich
    else
      ./gradlew test --tests "solution.Day${padded_day}Spec" --console=rich
    fi
    exit 0
  else
    input="$2"
  fi
elif [[ ! -f "$input" ]]; then
  echo "Fetching input for day $day"
  mkdir -p inputs
  status_code=$(curl -s -w "%{http_code}" -H "Cookie: session=$SESSION_COOKIE" -o "$input.tmp" "https://adventofcode.com/2024/day/$day/input")
  if [[ "$status_code" -eq 200 ]]; then
      mv "$input.tmp" "$input"
  else
      echo "Failed to fetch input. HTTP status code: $status_code"
      rm -f "$input.tmp"  # Clean up the temporary file if it exists
      exit 1
  fi
fi

./gradlew run --args="-days $day -input $input" -q --console=plain
