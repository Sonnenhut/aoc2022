package com.aoc2022.day

import kotlin.math.max

class Day12 : GenericDay() {
    override fun solve1(input: List<String>): Int {
        val world: List<List<Char>> = input.map { it.toList() }
        val startPos = world.positionOf('S')
        val endPos = world.positionOf('E')

        return world.dijkstra(startPos) { currHeight, nextHeight ->
            max(0, nextHeight - currHeight) in 0..1
        }[endPos]!!
    }

    override fun solve2(input: List<String>): Int {
        val world: List<List<Char>> = input.map { it.toList() }
        val endPos = world.positionOf('E')

        return world.dijkstra(endPos) { currHeight, nextHeight ->
            // we start from the top, so inverse the height chechkng.
            max(0, currHeight - nextHeight) in 0..1
        }
            .filter { entry: Map.Entry<Pair<Int, Int>, Int> -> world.at(entry.key) == 'a' }
            .minBy { it.value }
            .value
    }

    private fun List<List<Char>>.positionOf(char: Char): Pair<Int, Int> {
        return this.mapIndexed { y, row -> Pair(row.indexOf(char), y) }.find { it.first != -1 }!!
    }

    private fun List<List<Char>>.at(pos: Pair<Int, Int>): Char {
        val (x, y) = pos
        return this[y][x]
    }


    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(this.first + other.first, this.second + other.second)
    }

    fun Char.height(): Char {
        return when (this) {
            'S' -> 'a'
            'E' -> 'z'
            else -> this
        }
    }

    // https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
    fun List<List<Char>>.dijkstra(
        start: Pair<Int, Int>,
        canClimb: (Char, Char) -> Boolean
    ): Map<Pair<Int, Int>, Int> {
        val queue =
            this.mapIndexed { y, row -> row.indices.map { Pair(it, y) } }.flatten().toMutableList()
        val dist: MutableMap<Pair<Int, Int>, Int> =
            queue.associateWith { Int.MAX_VALUE }.toMutableMap()
        val prev: MutableMap<Pair<Int, Int>, Pair<Int, Int>?> =
            queue.associateWith { null }.toMutableMap()

        dist[start] = 0

        while (queue.isNotEmpty()) {
            val u = queue.minBy { dist[it]!! }
            queue.remove(u)

            val surround = listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))
                .map { u + it }
                .filter { it.first in 0..this[0].indices.max() && it.second in 0..this.indices.max() } // valid positions
            for (v in surround) {
                val heightDifference = canClimb(this.at(u).height(), this.at(v).height())
                val alt = if (dist[u]!! == Int.MAX_VALUE) {
                    Int.MAX_VALUE
                } else {
                    dist[u]!! + 1
                }
                if (heightDifference && alt < dist[v]!!) {
                    dist[v] = alt
                    prev[v] = u
                }
            }
        }
        return dist
    }
}

