#!/usr/bin/bash

# If you want to auto-fetch inputs for your advent of code puzzles,
# you can set up SESSION_COOKIE env variable here or use something like direnv to set it
# export SESSION_COOKIE="..."

aoc() {
  if [[ "$#" -eq 0 ]]; then
    echo "Usage: aoc <day> [<year>] [<input>]"
    return 1
  fi
  ./run.sh "$@"
}

aot() {
  if [[ "$#" -eq 1 ]]; then
    ./run.sh "$@" test
  else
    ./run.sh 0 test
  fi
}
