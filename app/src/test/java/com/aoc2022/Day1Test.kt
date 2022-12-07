package com.aoc2022

import com.aoc2022.day.GenericDay
import com.aoc2022.day.Day1
import org.junit.Test

import org.junit.Assert.*
import java.io.File

class Day1Test {

    @Test
    fun pt1_exampleInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(EXAMPLE_INPUT)
        assertEquals(24000, res)
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val myInput = FILE_INPUT
        val res = tut.solve1(myInput)
        assertEquals(70613, res)
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(EXAMPLE_INPUT)
        assertEquals(45000, res)
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        val myInput = FILE_INPUT
        val res = tut.solve2(myInput)
        assertEquals(205805, res)
    }

    companion object {
        private val DAY_CLASS = Day1::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }
        fun tutInstance() : GenericDay {
            return DAY_CLASS.newInstance()
        }
        val EXAMPLE_INPUT = """
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
    """.trimIndent().lines()
    }
}