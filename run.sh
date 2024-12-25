#!/usr/bin/bash

# Provide a session cookie environment variable SESSION_COOKIE
day="$1"
shift
padded_day=$(printf "%02d" "$day")

if [[ "$1" =~ ^[0-9]{4}$ ]]; then
  year="$1"
  shift
else
  current_year=$(date +%Y)
  current_month_day=$(date +%m%d)
  # Set the default year based on if the current day is before or after December 1st
  if [ "$current_month_day" -lt "1201" ]; then
    default_year=$((current_year - 1))
  else
    default_year=$current_year
  fi
  year="$default_year"
fi

input="inputs/$year/Day$padded_day.txt"
if [[ "$#" -gt 0 ]]; then
  if [[ "$1" == "test" ]]; then
    input="inputs/$year/example/Day$padded_day.txt"
  else
    input="$1"
  fi
elif [[ ! -f "$input" ]]; then
  echo "Fetching input for $day $year"
  mkdir -p "inputs/$year"
  aoc_url="https://adventofcode.com/$year/day/$day/input"
  status_code=$(curl -s -w "%{http_code}" -H "Cookie: session=$SESSION_COOKIE" -o "$input.tmp" "$aoc_url")
  if [[ "$status_code" -eq 200 ]]; then
      mv "$input.tmp" "$input"
  else
      echo "Failed to fetch input. HTTP status code: $status_code"
      rm -f "$input.tmp"  # Clean up the temporary file if it exists
      exit 1
  fi
fi

./gradlew run --args="-d $day -y $year -i $input" -q --console=plain
