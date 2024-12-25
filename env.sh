#!/usr/bin/bash

# If you want to auto-fetch inputs for your advent of code puzzles,
# you can set up SESSION_COOKIE env variable here or use something like direnv to set it
# export SESSION_COOKIE="..."

aoc() {
  if [[ "$#" -gt 0 ]]; then
    ./run.sh "$@"
  else
    echo "Usage: aoc <day> [<year>] [<input>]"
  fi
}

aot() {
  if [[ "$#" -gt 0 ]]; then
    ./run.sh "$@" test
  else
    echo "Usage: aot <day> [<year>]"
  fi
}
