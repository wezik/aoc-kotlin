# Advent of Code

My solutions to [Advent of Code](https://adventofcode.com/) puzzles in Kotlin!

## Years completed

2024: 50/50⭐
2025: 18/24⭐

Missing implementations:

- Day 24 (part 2): solution is mostly manual, it's difficult to implement in code

## Requirements to run

- JDK 21

## Installation from source

### Linux/macOS

```bash
git clone https://github.com/wezik/aoc-kotlin.git
cd aoc-kotlin
./gradlew buildFat
source ./alias.sh
```

### Windows

```ps1
git clone https://github.com/wezik/aoc-kotlin.git
cd aoc-kotlin
.\gradlew buildFat
```

## How to use

This project provides 2 separate CLI entrypoints: [aoc](#aoc) and [aot](#aot).  
These are executed via shell or batch scripts, allowing you to use them like a regular CLI tool.  
The entrypoint scripts are:

- [alias.sh](alias.sh) for Linux/macOS - defines `aoc` and `aot` functions
- [aoc.bat](aoc.bat) for Windows - runs `aoc`
- [aot.bat](aot.bat) for Windows - runs `aot`

> [!NOTE]
> These scripts are meant to be run from the project directory,  
> they *do not install anything globally* or add commands to your system `PATH`.

After building the project (and sourcing aliases for Linux/macOS with `source ./alias.sh`)  
You can invoke them like:

```bash
aoc -d 5
aot -d 3 --path "inputs/Day03.txt"
```

Or in Powershell:

```ps1
.\aoc.bat -d 5
.\aot.bat -d 3 --path "inputs/Day03.txt"
```

### AOC

Loads inputs directly from Advent of Code website to run solutions against.

> [!IMPORTANT]
> It runs against advent of code inputs, which requires session cookie, you can either:
>
> - export ADVENT_COOKIE ex. `export ADVENT_COOKIE="xxx"`
> - provide session cookie via option ex. `-s "session_cookie"`

```bash
Usage: aoc [<options>]

Options:
  -d, --day=<int>              Day
  -y, --year=<int>             Year (defaults to last advent of code year)
  -s, --session-cookie=<text>  Session cookie (defaults to "ADVENT_COOKIE" env variable)
  -h, --help                   Show this message and exit
```

### AOT

Loads inputs from files instead of running solutions against real ones.  
You can provide `--path` to load custom input, otherwise it will load examples.

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
