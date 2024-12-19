# Advent of Code 2024

My solutions for 2024 Advent of Code, there is also a custom action that uploads [benchmarks](https://github.com/wezik/aoc-2024/blob/benchmarks/results.md) on demand

## Requirements
- JDK 21

## How to run
- Put your inputs in `inputs/` directory with names `DAY01` `DAY02` etc. with no format.
- Run with `./gradlew run`
- (Optionally) if you don't input all 25 days to default directory or have different format, use appropiate arguments as specified below:

## Running the Program with Arguments
To run the program with custom arguments, use the following command format: `./gradlew run args="{arguments}"`  
Example Usage: `./gradlew run args="-days 1-15,20 -input 0,0,inputs/day3.txt,inputs/day4.txt"` etc.  
(Note: The usage of arguments may not be the most elegant, I plan to change it somehow later.)

Available arguments:
| Argument          | Description                                      |
|-------------------|--------------------------------------------------|
| `-days [ranges]`  | Comma-eparated list of ranges for days to solve (both ends inclusive) ex. `1,2,5-16`
| `-format markdown`| Outputs a markdown file with results.
| `-input [path\|0]`   | Comma-separated list of inputs, in the same order as days, use 0 for default location
| `-benchmark [runs]` | Run benchmarks instead of solving the solutions, pass in amount of solves per day
| `-force [true\|false]` | Usable only with `-benchmark` on true lets you skip the hardcoded limits for benchmarks for certain days
| `-q [true\|false]`| (Planned for removal) Quiet mode, simplifies output and doesn't print logs.  
