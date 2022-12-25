package com.aoc2022.day

import kotlin.math.abs

class Day15 : GenericDay() {

    override fun solve1(input: List<String>): Int {
        return solve1(input, 2000000)
    }

    fun solve1(input: List<String>, inRow: Int): Int {
        val sensors = input.map { Sensor.parse(it) }
        val takenLocs = sensors.flatMap { listOf(it.location, it.beacon) }.toSet()
        val maxDistance = sensors.maxOf { it.maxDist }
        val maxX: Int = takenLocs.map { it.first }.maxBy { it } + maxDistance
        val minX: Int = takenLocs.map { it.first }.minBy { it } - maxDistance

        val inRangeByAnySensor: (Pair<Int, Int>) -> Boolean =
            { coord -> sensors.firstOrNull { sensor -> sensor.isInRange(coord) } != null }

        val impossibleBeaconLoc = mutableListOf<Pair<Int, Int>>()
        for (x in minX..maxX) {
            val coord = Pair(x, inRow)
            if (!takenLocs.contains(coord) && inRangeByAnySensor(coord)) {
                impossibleBeaconLoc.add(coord)
            }
        }
        return impossibleBeaconLoc.size
    }

    override fun solve2(input: List<String>): Long {
        return solve2(input, 4000000)
    }

    fun solve2(input: List<String>, xyBound: Int): Long {
        val tuningFreq = 4000000L

        val sensors = input.map { Sensor.parse(it) }
        val takenLocs = sensors.flatMap { listOf(it.location, it.beacon) }.toSet()

        var res: Pair<Int, Int>? = null
        for (sensor in sensors) {
            val possibleDistressSignals = sensor.justOutOfReach()
                .filter { it.first in 0..xyBound && it.second in 0..xyBound }
                .filter { !takenLocs.contains(it) }

            val possible = possibleDistressSignals.firstOrNull { distress ->
                sensors.none {
                    it.isInRange(distress)
                }
            }
            if (possible != null) {
                res = possible
                break
            }
        }
        return res!!.first * tuningFreq + res.second
    }

    class Sensor(private val loc: Pair<Int, Int>, private val foundBeacon: Pair<Int, Int>) {
        val maxDist = beacon.manhattanDist(loc)
        val location get() = loc
        val beacon get() = foundBeacon

        fun isInRange(coord: Pair<Int, Int>): Boolean {
            return loc.manhattanDist(coord) <= maxDist
        }

        fun justOutOfReach() =
            sequence { // yield the values in a sequence, otherwise it's gonna blow up the heap...
                val wantedDist = maxDist + 1
                for (off1 in 0..wantedDist) {
                    val off2 = wantedDist - off1
                    yield(Pair(loc.first - off1, loc.second + off2))
                    yield(Pair(loc.first - off1, loc.second - off2))
                    yield(Pair(loc.first + off1, loc.second + off2))
                    yield(Pair(loc.first + off1, loc.second - off2))
                }
            }


        private fun Pair<Int, Int>.manhattanDist(other: Pair<Int, Int>): Int {
            return abs(other.first - this.first) + abs(other.second - this.second)
        }

        override fun toString(): String {
            return "Sensor x=${loc.first}, y=${loc.second}, maxDist=$maxDist"
        }

        companion object {
            fun parse(line: String): Sensor {
                //Sensor at x=2, y=18: closest beacon is at x=-2, y=15
                val (sensorXy, beaconXy) = line.replace("Sensor at ", "")
                    .split(": closest beacon is at ")
                val (sensorX, sensorY) = sensorXy.replace("x=", "").replace("y=", "").split(", ")
                    .map { it.toInt() }
                val (beaconX, beaconY) = beaconXy.replace("x=", "").replace("y=", "").split(", ")
                    .map { it.toInt() }

                return Sensor(Pair(sensorX, sensorY), Pair(beaconX, beaconY))
            }
        }
    }
}
