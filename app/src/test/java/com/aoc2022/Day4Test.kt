package com.aoc2022

import com.aoc2022.day.Day4
import com.aoc2022.day.GenericDay
import com.aoc2022.overview.Day
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day4Test {

    @Test
    fun pt1_exampleInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(EXAMPLE_INPUT)
        assertEquals(2, res)
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(FILE_INPUT)
        assertEquals(433, res) // 493 too high
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(EXAMPLE_INPUT)
        assertEquals(4, res)
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(FILE_INPUT)
        assertEquals(852, res)
    }

    companion object {
        private val DAY_CLASS = Day4::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }

        fun tutInstance(): GenericDay {
            return DAY_CLASS.newInstance()
        }

        val EXAMPLE_INPUT = """
2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8
    """.trimIndent().lines()
    }
}