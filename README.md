# Advent of Code
My solutions to [Advent of Code puzzles](https://adventofcode.com/) in Kotlin!

## Years completed
2024: 50/50⭐ (not all solutions are implemented in repo yet)

## Requirements to run
- JDK 21

## Setup
- clone the repo `git clone https://github.com/wezik/aoc-kotlin.git`
- enter `cd aoc-kotlin` 
- build fat jar with `./gradlew buildFat`
- source aliases with `source ./alias.sh`

or simply copy and paste in terminal:
```bash
git clone https://github.com/wezik/aoc-kotlin.git
cd aoc-kotlin
./gradlew buildFat
source ./alias.sh
```

now run the CLI (look [below](#how-to-use) for more details)

## How to use
This project is split into 2 separate CLI entrypoints:
- `aoc` core
- `aot` test runner

### AOC
Serves as the core CLI entrypoint to run solutions

> [!NOTE]  
> `aoc` entrypoint runs against real advent of code inputs, you have to either export `ADVENT_COOKIE` value from your advent of code session or provide `--session-cookie` option

```bash
Usage: aoc [<options>]

Options:
  -d, --day=<int>              Day
  -y, --year=<int>             Year (defaults to last advent of code year)
  -s, --session-cookie=<text>  Session cookie (defaults to "ADVENT_COOKIE" env variable)
  -h, --help                   Show this message and exit
```

### AOT
Serves as the test runner entrypoint, it will run solutions against example inputs or your custom input file if you provide `--path` option.

```bash
Usage: aot [<options>]

Options:
  -d, --day=<int>    Day
  -y, --year=<int>   Year (defaults to last advent of code year)
  -p, --path=<path>  Path with custom input file to load
  -h, --help         Show this message and exit
```

## TODO
- [ ] Introduce releases so it's not necessary to build from source
