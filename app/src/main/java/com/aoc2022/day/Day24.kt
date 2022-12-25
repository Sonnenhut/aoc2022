package com.aoc2022.day

import java.util.PriorityQueue

class Day24 : GenericDay() {

    override fun solve1(input: List<String>): Int {
        val dimensions = Pair(input[0].length, input.size)
        val start = Pair(1,0)
        val end = Pair(dimensions.first - 2, dimensions.second - 1)

        val blizzards = Blizzard.parseAll(input)
        return dijkstra(CoordAtCycle(start, 0), end, blizzards).second
    }

    override fun solve2(input: List<String>): Int {
        val dimensions = Pair(input[0].length, input.size)
        val start = Pair(1,0)
        val end = Pair(dimensions.first - 2, dimensions.second - 1)

        val blizzards = Blizzard.parseAll(input)
        val (firstGoalCycle, firstGoalTime) = dijkstra(CoordAtCycle(start, 0), end, blizzards)
        val (backAtCycle, backTime) = dijkstra(firstGoalCycle, start, blizzards)
        val (_, endTime) = dijkstra(backAtCycle, end, blizzards)
        return firstGoalTime + backTime + endTime
    }

    // https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
    fun dijkstra(
        startAtCycle: CoordAtCycle,
        end : Pair<Int, Int>,
        blizzards : List<Blizzard>
    ): Pair<CoordAtCycle, Int> {
        val maxCoords = blizzards.first().usableDim
        val cycleTime = lcm(maxCoords.first, maxCoords.second)

        val allCoords = (1..maxCoords.second).flatMap { y ->
            (1..maxCoords.first).map { x -> Pair(x,y) }
        } + startAtCycle.coord + end

        val vertices : Set<CoordAtCycle> = (0 until cycleTime).flatMap { cycle ->
            val cycleBlizzards = blizzards.map { it.move(cycle) }.toSet()
            (allCoords - cycleBlizzards).map { CoordAtCycle(it, cycle) }
        }.toSet()

        val dist : MutableMap<CoordAtCycle, Int> = vertices.associateWith { Int.MAX_VALUE }.toMutableMap()
        val prev : MutableMap<CoordAtCycle, CoordAtCycle?> = vertices.associateWith { null }.toMutableMap()

        dist[startAtCycle] = 0

        val queue = PriorityQueue { a: CoordAtCycle, b: CoordAtCycle -> dist[a]!!.compareTo(dist[b]!!) }
        queue.add(startAtCycle)

        while (queue.isNotEmpty()) {
            val u = queue.remove()
            val newCycleTime = (u.cycle + 1) % cycleTime

            val surround = listOf(Pair(0,0), Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))
                .map { CoordAtCycle(u.coord + it, newCycleTime) }
                .filter { it in vertices }

            for (v in surround) {
                val competing = inc(dist.getOrDefault(u, Int.MAX_VALUE))
                if(competing < dist.getOrDefault(v, Int.MAX_VALUE)) {
                    dist[v] = competing
                    prev[v] = u

                    // just add items along the way, duplicates will be filtered out eventually
                    queue.add(v)
                }
            }
        }

        val endAtCycle = dist.keys.filter { it.coord == end }.minBy { dist[it]!! }
        return Pair(endAtCycle, dist[endAtCycle]!!)
    }

    // increments an integer up to Int.MAX_VALUE
    fun inc(num: Int): Int {
        return if(num == Int.MAX_VALUE) {
            Int.MAX_VALUE
        } else {
            num + 1
        }
    }

    data class CoordAtCycle(val coord: Pair<Int, Int>, val cycle: Int) {
        override fun toString(): String {
            return "$coord , $cycle"
        }
    }

    class Blizzard(val coord : Pair<Int, Int>, val direction : Pair<Int, Int>, val mazeDim : Pair<Int, Int>) {
        private val wallOff = Pair(1,1)
        val usableDim = mazeDim - (wallOff + wallOff)

        fun move(amt : Int) : Pair<Int, Int> {
            val moveAmt = Pair(amt, amt)
            var res = coord
            res -= wallOff
            res += direction * moveAmt
            res = res.mod(usableDim)
            res += wallOff

            return res
        }

        override fun toString(): String {
            return "$coord"
        }
        companion object {
            fun parse(x: Int, y: Int, char: Char, mazeDim: Pair<Int, Int>) : Blizzard? {
                val direction = when(char) {
                    '<' -> Pair(-1,0)
                    '>' -> Pair(1,0)
                    '^' -> Pair(0,-1)
                    'v' -> Pair(0,1)
                    else -> null
                }
                return if(direction == null) {
                    null
                } else {
                    Blizzard(Pair(x,y), direction, mazeDim)
                }
            }

            fun parseAll(input : List<String>) : List<Blizzard> {
                val mazeDim = Pair(input[0].length, input.size)
                val blizzards = mutableListOf<Blizzard>()
                for((y, row) in input.withIndex()) {
                    for ((x, char) in row.withIndex()) {
                        parse(x,y,char,mazeDim)?.let {
                            blizzards += it
                        }
                    }
                }
                return blizzards
            }
        }
    }

    // lowest common multiple of two numbers
    fun lcm(n1: Int, n2: Int) : Int {
        var lcm = if (n1 > n2) n1 else n2

        while (true) {
            if (lcm % n1 == 0 && lcm % n2 == 0) {
                break
            }
            ++lcm
        }
        return lcm
    }
}
