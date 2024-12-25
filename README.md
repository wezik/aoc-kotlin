# Advent of Code
My solutions to [Advent of Code puzzles](https://adventofcode.com/) in Kotlin!  

## Years completed
2024: 50/50⭐

## Requirements
- JDK 21

## Set-up
- (optional) export `SESSION_COOKIE` value from your advent of code session, see [env.sh](./env.sh)
  - (for concerned) It is only used to fetch inputs in [run.sh](./run.sh)
- run `source ./env.sh` to set up aliases

## How to run
- use `aoc <day> <year?> <input?>`
- use `aot <day> <year?>` to run example input

### Details
`<day>` is **required** for both aliases, specifies the day to run the puzzle solver for  
`<year?>` is **optional** for both, can be passed or will default to most up-to-date advent of code  
`<input?>` is **optional** for `aoc` only, specifies the input path, if not set it defaults to `./inputs/$year/Day$0paddedDay.txt`

#### Valid examples
`aoc 1` - Runs day `1` of most-recent advent of code, fetches input if cookie provided and reads it from `./inputs/$year/Day01.txt`  
`aoc 1 2020` - Runs day `1` of `2020` advent of code, fetches input if cookie provided and reads it from `./inputs/2020/Day01.txt`  
`aoc 1 foo/bar.txt` - Runs day `1` of most-recent advent of code, and reads input from `./foo/bar.txt`  
`aoc 1 2020 foo/bar.txt` - etc...
