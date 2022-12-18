package com.aoc2022.day

import kotlin.math.exp

class Day18 : GenericDay() {

    override fun solve1(input: List<String>): Int {
        val cubes = input.parse()
        return (cubes.flatMap { it.around() } - cubes.toSet()).size
    }

    override fun solve2(input: List<String>): Int {
        val cubes = input.parse()

        val xBounds = (cubes.minOf { it.first }-1)..(cubes.maxOf { it.first }+1)
        val yBounds =  (cubes.minOf { it.second }-1)..(cubes.maxOf { it.second }+1)
        val zBounds =  (cubes.minOf { it.third }-1)..(cubes.maxOf { it.third }+1)

        val isOutsideBounds : (Triple<Int, Int, Int>) -> Boolean = { check ->
            check.first !in xBounds || check.second !in yBounds || check.third !in zBounds
        }

        // check all connected space (air) outside of the object
        val negative = mutableSetOf(Triple(xBounds.first,yBounds.first,zBounds.first))
        do {
            val lastSize = negative.size

            negative.addAll(negative.flatMap { it.around() })
            negative.removeAll { isOutsideBounds(it) || cubes.contains(it) }

        } while (negative.size != lastSize)

        val hollowSpace = cubes.flatMap { it.around() }.toSet() - negative - cubes

        return (cubes.flatMap { it.around() } - cubes - hollowSpace).size
    }

    private fun Triple<Int, Int, Int>.around() : Set<Triple<Int, Int, Int>> {
        return listOf(Triple(0,0,1),
            Triple(0,0,-1),
            Triple(0,1,0),
            Triple(0,-1,0),
            Triple(1,0,0),
            Triple(-1,0,0)
        ).map { this + it }.toSet()
    }

    private operator fun Triple<Int, Int, Int>.plus(other: Triple<Int, Int, Int> ) : Triple<Int, Int, Int> {
        return Triple(this.first + other.first, this.second + other.second, this.third + other.third)
    }

    private fun List<String>.parse() : Set<Triple<Int, Int, Int>> {
        return this.map {
            val (x,y,z) = it.split(",")
            Triple(x.toInt(),y.toInt(),z.toInt())
        }.toSet()
    }

}
