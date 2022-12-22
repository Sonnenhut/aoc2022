package com.aoc2022

import com.aoc2022.day.Day22
import com.aoc2022.day.GenericDay
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day22Test {

    @Test
    fun pt1_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(6032, tut.solve1(EXAMPLE_INPUT))
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(FILE_INPUT)
        println(res)
        assertEquals(136054, res)
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(5031, tut.solve2Example(EXAMPLE_INPUT))
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(FILE_INPUT)
        println(res)
        assertEquals(122153, res)
    }

    companion object {
        private val DAY_CLASS = Day22::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }

        fun tutInstance(): Day22 {
            return DAY_CLASS.newInstance()
        }

        val EXAMPLE_INPUT = """
        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5
        """.trimIndent().lines()
    }
}