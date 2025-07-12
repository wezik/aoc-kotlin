#!/usr/bin/sh

function aoc() {
	./gradlew runAoc --args="$*" -q
}

function aot() {
	./gradlew runAot --args="$*" -q
}
