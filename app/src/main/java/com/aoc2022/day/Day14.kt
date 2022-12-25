package com.aoc2022.day

import kotlin.math.max
import kotlin.math.min

class Day14 : GenericDay() {

    override fun solve1(input: List<String>): Int {
        val walls = parseAllPoints(input)
        val sand = buildSandCastle(walls)

        return sand.size
    }

    override fun solve2(input: List<String>): Int {
        val walls = parseAllPoints(input)
        val sand = buildSandCastle(walls, true)

        return sand.size
    }

    private fun buildSandCastle(
        walls: Set<Pair<Int, Int>>,
        withGround: Boolean = false
    ): Set<Pair<Int, Int>> {
        val lowerBound = walls.maxOfOrNull { it.second }!!
        val groundY = lowerBound + 2
        val start = Pair(500, 0)
        val moves = listOf(Pair(0, 1), Pair(-1, 1), Pair(1, 1))

        val sand = mutableSetOf<Pair<Int, Int>>()
        val canReach: (Pair<Int, Int>) -> Boolean = { coord ->
            val isAtBottom = if (withGround) {
                coord.second >= groundY
            } else {
                false
            }
            !isAtBottom && !walls.contains(coord) && !sand.contains(coord)
        }

        var prevCorn: Pair<Int, Int>
        var corn = start
        do {
            prevCorn = corn

            corn = moves.map { it + corn }
                .firstOrNull { canReach(it) } ?: corn

            if (corn == prevCorn) {
                sand.add(corn)
                corn = start
            }
            val outOfBounds = !withGround && corn.second >= lowerBound
            val cornAtTop = corn == start && prevCorn == corn
        } while (!cornAtTop && !outOfBounds)
        return sand
    }

    fun parseAllPoints(lines: List<String>): Set<Pair<Int, Int>> {
        return lines.map { parseToPoints(it) }
            .fold(setOf()) { acc, next ->
                acc.union(next)
            }
    }

    fun parseToPoints(line: String): Set<Pair<Int, Int>> {
        // 498,4 -> 498,6 -> 496,6
        return line.split(" -> ")
            .map {
                val nums = it.split(",").map { it.toInt() }
                Pair(nums[0], nums[1])
            }
            .windowed(2, 1, false)
            .fold(setOf()) { acc, points ->
                val (pt1, pt2) = points
                val (pt1x, pt1y) = pt1
                val (pt2x, pt2y) = pt2

                val newPoints = if (pt1x == pt2x) {
                    (min(pt1y, pt2y)..max(pt1y, pt2y)).map { Pair(pt1x, it) }
                } else if (pt1y == pt2y) {
                    (min(pt1x, pt2x)..max(pt1x, pt2x)).map { Pair(it, pt1y) }
                } else {
                    throw IllegalArgumentException("Unable to parse input, cannot draw diagonal walls.")
                }

                acc.union(newPoints)
            }
    }
}
