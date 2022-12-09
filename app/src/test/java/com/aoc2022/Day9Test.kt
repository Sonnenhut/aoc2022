package com.aoc2022

import com.aoc2022.day.Day9
import com.aoc2022.day.GenericDay
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day9Test {

    @Test
    fun pt1_exampleInput1_isCorrect() {
        val tut = tutInstance()
        assertEquals(13, tut.solve1(EXAMPLE_INPUT))
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(FILE_INPUT)
        assertEquals(5930, res)
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(1, tut.solve2(EXAMPLE_INPUT))
    }
    @Test
    fun pt2_biggerExampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(36, tut.solve2(EXAMPLE_BIGGER_INPUt))
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(FILE_INPUT)
        assertEquals(2443, res)
    }

    companion object {
        private val DAY_CLASS = Day9::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }

        fun tutInstance(): GenericDay {
            return DAY_CLASS.newInstance()
        }

        val EXAMPLE_INPUT = """
R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2
        """.trimIndent().lines()
        val EXAMPLE_BIGGER_INPUt = """
R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20
        """.trimIndent().lines()
    }
}