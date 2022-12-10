package com.aoc2022

import com.aoc2022.day.Day10
import com.aoc2022.day.GenericDay
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

@Suppress("UNCHECKED_CAST")
class Day10Test {

    @Test
    fun pt1_exampleInput1_isCorrect() {
        val tut = tutInstance()
        assertEquals(13140, tut.solve1(EXAMPLE_INPUT))
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(FILE_INPUT)
        assertEquals(15020, res)
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        val res : List<String> = tut.solve2(EXAMPLE_INPUT) as List<String>
        res.forEach { println(it) }
        assertEquals(EXAMPLE_PT2_RES, res)
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        val res : List<String> = tut.solve2(FILE_INPUT) as List<String>
        res.forEach { println(it) }
        assertEquals(INPUT_PT2_RES, res) // EFUGLPAP
    }

    companion object {
        private val DAY_CLASS = Day10::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }

        fun tutInstance(): GenericDay {
            return DAY_CLASS.newInstance()
        }

        val EXAMPLE_PT2_RES = """
##..##..##..##..##..##..##..##..##..##..
###...###...###...###...###...###...###.
####....####....####....####....####....
#####.....#####.....#####.....#####.....
######......######......######......####
#######.......#######.......#######.....
        """.trimIndent().lines()

        val INPUT_PT2_RES = """
####.####.#..#..##..#....###...##..###..
#....#....#..#.#..#.#....#..#.#..#.#..#.
###..###..#..#.#....#....#..#.#..#.#..#.
#....#....#..#.#.##.#....###..####.###..
#....#....#..#.#..#.#....#....#..#.#....
####.#.....##...###.####.#....#..#.#....
        """.trimIndent().lines()

        val EXAMPLE_INPUT = """
            addx 15
            addx -11
            addx 6
            addx -3
            addx 5
            addx -1
            addx -8
            addx 13
            addx 4
            noop
            addx -1
            addx 5
            addx -1
            addx 5
            addx -1
            addx 5
            addx -1
            addx 5
            addx -1
            addx -35
            addx 1
            addx 24
            addx -19
            addx 1
            addx 16
            addx -11
            noop
            noop
            addx 21
            addx -15
            noop
            noop
            addx -3
            addx 9
            addx 1
            addx -3
            addx 8
            addx 1
            addx 5
            noop
            noop
            noop
            noop
            noop
            addx -36
            noop
            addx 1
            addx 7
            noop
            noop
            noop
            addx 2
            addx 6
            noop
            noop
            noop
            noop
            noop
            addx 1
            noop
            noop
            addx 7
            addx 1
            noop
            addx -13
            addx 13
            addx 7
            noop
            addx 1
            addx -33
            noop
            noop
            noop
            addx 2
            noop
            noop
            noop
            addx 8
            noop
            addx -1
            addx 2
            addx 1
            noop
            addx 17
            addx -9
            addx 1
            addx 1
            addx -3
            addx 11
            noop
            noop
            addx 1
            noop
            addx 1
            noop
            noop
            addx -13
            addx -19
            addx 1
            addx 3
            addx 26
            addx -30
            addx 12
            addx -1
            addx 3
            addx 1
            noop
            noop
            noop
            addx -9
            addx 18
            addx 1
            addx 2
            noop
            noop
            addx 9
            noop
            noop
            noop
            addx -1
            addx 2
            addx -37
            addx 1
            addx 3
            noop
            addx 15
            addx -21
            addx 22
            addx -6
            addx 1
            noop
            addx 2
            addx 1
            noop
            addx -10
            noop
            noop
            addx 20
            addx 1
            addx 2
            addx 2
            addx -6
            addx -11
            noop
            noop
            noop
        """.trimIndent().lines()
    }
}