package com.aoc2022.day

import kotlin.math.absoluteValue

class Day9 : GenericDay() {
    override fun solve1(input: List<String>): Int {
        val commands: List<Direction> = parseDirections(input)

        val rope = (1 .. 2).map { Pair(0,0) }.toMutableList()
        return simulateRopeMoves(commands, rope)
    }

    override fun solve2(input: List<String>): Int {
        val commands: List<Direction> = parseDirections(input)

        val rope = (1 .. 10).map { Pair(0,0) }.toMutableList()
        return simulateRopeMoves(commands, rope)
    }

    private fun parseDirections(input: List<String>): List<Direction> {
        val commands: List<Direction> = input.flatMap {
            val (directionStr, amtStr) = it.split(" ")
            (0 until amtStr.toInt()).map { Direction.valueOf(directionStr.substring(0, 1)) }
        }
        return commands
    }

    private fun simulateRopeMoves(
        commands: List<Direction>,
        rope: MutableList<Pair<Int, Int>>
    ): Int {
        val tailVisits = mutableSetOf(Pair(0, 0))

        for (command in commands) {
            var moving = rope.first() + command.coords
            rope[0] = moving

            for ((idx, dragging) in rope.withIndex().toList().subList(1, rope.size)) {
                if (!moving.touches(dragging)) {
                    rope[idx] = moving.pull(dragging)
                }
                moving = rope[idx]
            }
            tailVisits.add(rope.last())
        }

        return tailVisits.size
    }

    private fun Pair<Int, Int>.touches(other: Pair<Int, Int>): Boolean {
        return this.distance(other)
            .toList()
            .find { it.absoluteValue > 1 } == null
    }

    private fun Pair<Int, Int>.distance(other: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(this.first - other.first, this.second - other.second)
    }

    private fun Pair<Int, Int>.pull(other: Pair<Int, Int>): Pair<Int, Int> {
        val fullDist = Pair(this.first - other.first, this.second - other.second)
        val dist = Pair(fullDist.first.coerceIn(-1..1), fullDist.second.coerceIn(-1..1))
        return other + dist
    }

    enum class Direction(private val x: Int, private val y: Int) {
        R(1, 0),
        L(-1, 0),
        U(0, 1),
        D(0, -1);

        val coords get() = Pair(x, y)
    }
}

