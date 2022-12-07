package com.aoc2022

import com.aoc2022.day.GenericDay
import com.aoc2022.day.Day2
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day2Test {

    @Test
    fun pt1_exampleInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(EXAMPLE_INPUT)
        assertEquals(15, res)
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val myInput = FILE_INPUT
        val res = tut.solve1(myInput)
        assertEquals(11150, res)
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(EXAMPLE_INPUT)
        assertEquals(12, res)
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(FILE_INPUT)
        assertEquals(8295, res)
    }

    companion object {
        private val DAY_CLASS = Day2::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }
        fun tutInstance() : GenericDay {
            return DAY_CLASS.newInstance()
        }
        val EXAMPLE_INPUT = """
            A Y
            B X
            C Z
    """.trimIndent().lines()
    }
}