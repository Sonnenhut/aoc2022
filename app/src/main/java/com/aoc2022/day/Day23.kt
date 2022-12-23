package com.aoc2022.day

class Day23 : GenericDay() {

    override fun solve1(input: List<String>): Int {
        return solve(input, 10).first
    }

    fun solve(input: List<String>, maxIteration: Int): Pair<Int, Int> {
        var elves = input.flatMapIndexed { y, row ->
            row.mapIndexed { x, char ->
                if ('#' == char) {
                    Pair(x, y)
                } else {
                    null
                }
            }
        }.filterNotNull().toSet()

        var iteration = 0
        var lastElves = setOf<Pair<Int, Int>>()

        while (lastElves != elves && iteration != maxIteration) {
            lastElves = elves

            val direction = directions[iteration % directions.size]
            elves = moveElves(elves, direction)

            iteration++
        }

        val xSize = (elves.maxOf { it.first } - elves.minOf { it.first }) + 1
        val ySize = (elves.maxOf { it.second } - elves.minOf { it.second }) + 1
        val emptyGrounds = (xSize * ySize) - elves.size
        return Pair(emptyGrounds, iteration)
    }

    fun moveElves(
        elves: Set<Pair<Int, Int>>,
        startDirection: Pair<Int, Int>
    ): Set<Pair<Int, Int>> {
        val movingElves = elves.filter { elve -> elve.around().any { it in elves } }.toSet()
        val idleElves = elves - movingElves

        val proposedMoves = elves.associateBy { it }.toMutableMap()
        for (elve in movingElves) {
            for (directionOff in 0..3) {
                var directionIdx = directions.indexOf(startDirection) + directionOff
                directionIdx %= directions.size
                val direction = directions[directionIdx]
                val inView = directionViews()[direction]!!.map { it + elve }
                if (inView.none { elves.contains(it) }) {
                    proposedMoves[elve] = elve + direction
                    break // elve found a way, lets look at the next one..
                }
            }
        }

        // step 2, only move elves, that move to unique positions
        val futureFrom = proposedMoves.keys.groupBy { proposedMoves[it]!! }
        val duplicateFutures =
            proposedMoves.filter { entry -> futureFrom[entry.value]!!.size > 1 }
        val uniqueFutures = proposedMoves - duplicateFutures.keys

        return uniqueFutures.values.toSet() + idleElves + duplicateFutures.keys
    }

    fun directionViews(): Map<Pair<Int, Int>, List<Pair<Int, Int>>> {
        return mapOf(
            Pair(north, listOf(northWest, north, northEast)),
            Pair(south, listOf(southWest, south, southEast)),
            Pair(west, listOf(northWest, west, southWest)),
            Pair(east, listOf(northEast, east, southEast)),
        )
    }

    private fun Pair<Int, Int>.around(): List<Pair<Int, Int>> {
        return directionsWithDiagonal.map { it + this }
    }

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(this.first + other.first, this.second + other.second)
    }

    override fun solve2(input: List<String>): Int {
        return solve(input, -1).second
    }

    companion object {
        val north = Pair(0, -1)
        val south = Pair(0, 1)
        val west = Pair(-1, 0)
        val east = Pair(1, 0)
        val directions = listOf(north, south, west, east)
        val northWest = north + west
        val northEast = north + east
        val southWest = south + west
        val southEast = south + east
        val directionsWithDiagonal =
            listOf(north, south, west, east, northWest, southWest, northEast, southEast)
    }
}
