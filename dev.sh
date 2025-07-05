#!/usr/bin/sh

function aoc() {
	./gradlew run --args="$*" -q
}
