# Advent of Code
My solutions to [Advent of Code puzzles](https://adventofcode.com/) in Kotlin!

## Years completed
2024: 50/50⭐ (not all solutions are implemented in repo yet)

## Requirements
- JDK 21

## Setup
- clone the repo `git clone https://github.com/wezik/aoc-kotlin.git`
- enter `cd aoc-kotlin` 
- build fat jar with `./gradlew shadowJar`
- source an alias `source ./alias.sh`
- export `ADVENT_COOKIE` value from your advent of code session if you want to run against real inputs
- run `aoc` and enjoy!

## How to use
```bash
Usage: aoc [<options>]

Options:
  -d, --day=<int>              Day
  -y, --year=<int>             Year (defaults to the most recent)
  -s, --session-cookie=<text>  Session cookie
  -t, --test                   Runs against example file
  -p, --path=<path>            Path with custom input file to load
  -h, --help                   Show this message and exit
```

### Details
`--path` option and `--test` are mutually exclusive.

## TODO
- [ ] Introduce releases so it's not necessary to build from source
