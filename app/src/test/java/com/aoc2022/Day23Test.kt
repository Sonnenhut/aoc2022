package com.aoc2022

import com.aoc2022.day.Day22
import com.aoc2022.day.Day23
import com.aoc2022.day.GenericDay
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day23Test {

    @Test
    fun pt1_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(25, tut.solve1(EXAMPLE_INPUT))
    }
    @Test
    fun pt1_exampleBigInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(110, tut.solve1(EXAMPLE_BIG_INPUT))
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(FILE_INPUT)
        println(res)
        assertEquals(3940, res)
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(4, tut.solve2(EXAMPLE_INPUT))
    }

    @Test
    fun pt2_exampleBigInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(20, tut.solve2(EXAMPLE_BIG_INPUT))
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(FILE_INPUT)
        println(res)
        assertEquals(990, res)
    }

    companion object {
        private val DAY_CLASS = Day23::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }

        fun tutInstance(): GenericDay {
            return DAY_CLASS.newInstance()
        }

        val EXAMPLE_INPUT = """
.....
..##.
..#..
.....
..##.
.....
        """.trimIndent().lines()

        val EXAMPLE_BIG_INPUT = """
..............
..............
.......#......
.....###.#....
...#...#.#....
....#...##....
...#.###......
...##.#.##....
....#..#......
..............
..............
..............
        """.trimIndent().lines()
    }
}