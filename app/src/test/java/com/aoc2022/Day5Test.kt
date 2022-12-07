package com.aoc2022

import com.aoc2022.day.Day4
import com.aoc2022.day.Day5
import com.aoc2022.day.GenericDay
import com.aoc2022.overview.Day
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day5Test {

    @Test
    fun pt1_exampleInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(EXAMPLE_INPUT)
        assertEquals("CMZ", res)
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(FILE_INPUT)
        assertEquals("RNZLFZSJH", res)
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(EXAMPLE_INPUT)
        assertEquals("MCD", res)
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(FILE_INPUT)
        assertEquals("CNSFCGJSM", res)
    }

    companion object {
        private val DAY_CLASS = Day5::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }

        fun tutInstance(): GenericDay {
            return DAY_CLASS.newInstance()
        }

        val EXAMPLE_INPUT = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
    """.trimIndent().lines()
    }
}