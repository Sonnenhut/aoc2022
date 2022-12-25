package com.aoc2022

import com.aoc2022.day.Day24
import com.aoc2022.day.GenericDay
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day24Test {

    @Test
    fun pt1_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(18, tut.solve1(EXAMPLE_INPUT))
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(FILE_INPUT)
        println(res)
        assertEquals(251, res)
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(54, tut.solve2(EXAMPLE_INPUT))
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(FILE_INPUT)
        println(res)
        assertEquals(758, res)
    }

    companion object {
        private val DAY_CLASS = Day24::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }

        fun tutInstance(): GenericDay {
            return DAY_CLASS.newInstance()
        }

        val EXAMPLE_INPUT = """
#.######
#>>.<^<#
#.<..<<#
#>v.><>#
#<^v^^>#
######.#
        """.trimIndent().lines()
    }
}