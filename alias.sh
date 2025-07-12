#!/usr/bin/sh

function aoc() {
	java -jar build/libs/aoc.jar $@
}

function aot() {
	java -jar build/libs/aot.jar $@
}
