package com.aoc2022

import com.aoc2022.day.Day15
import com.aoc2022.day.GenericDay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.io.File

class Day15Test {

    @Test
    fun pt1_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(26, tut.solve1(EXAMPLE_INPUT, 10))
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(FILE_INPUT)
        println(res)
        assertNotEquals(4406715, res) // too low
        assertNotEquals(4408715, res) // too low
        assertEquals(5367037, res)
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(56000011, tut.solve2(EXAMPLE_INPUT, 20))
    }

    @Test
    fun pt2_justOutOfReach_isCorrect() {
        val sensor = Day15.Sensor.parse("Sensor at x=0, y=0: closest beacon is at x=0, y=1")
        val expected = setOf ( // expect locations with manhattanDistance 2
            Pair(-2,0),Pair(-1,1),Pair(0,2),
            Pair(1,1), Pair(2,0),
            Pair(1,-1), Pair(0,-2), Pair(-1,-1)
        )
        assertEquals(expected, sensor.justOutOfReach().toSet())
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(FILE_INPUT)
        println(res)
        assertNotEquals(343970184L, res) // too low
        assertEquals(11914583249288L, res)
    }

    companion object {
        private val DAY_CLASS = Day15::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }

        fun tutInstance(): Day15 {
            return DAY_CLASS.newInstance()
        }

        val EXAMPLE_INPUT = """
Sensor at x=2, y=18: closest beacon is at x=-2, y=15
Sensor at x=9, y=16: closest beacon is at x=10, y=16
Sensor at x=13, y=2: closest beacon is at x=15, y=3
Sensor at x=12, y=14: closest beacon is at x=10, y=16
Sensor at x=10, y=20: closest beacon is at x=10, y=16
Sensor at x=14, y=17: closest beacon is at x=10, y=16
Sensor at x=8, y=7: closest beacon is at x=2, y=10
Sensor at x=2, y=0: closest beacon is at x=2, y=10
Sensor at x=0, y=11: closest beacon is at x=2, y=10
Sensor at x=20, y=14: closest beacon is at x=25, y=17
Sensor at x=17, y=20: closest beacon is at x=21, y=22
Sensor at x=16, y=7: closest beacon is at x=15, y=3
Sensor at x=14, y=3: closest beacon is at x=15, y=3
Sensor at x=20, y=1: closest beacon is at x=15, y=3
        """.trimIndent().lines()
    }
}