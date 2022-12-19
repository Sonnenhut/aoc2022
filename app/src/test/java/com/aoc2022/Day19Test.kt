package com.aoc2022

import com.aoc2022.day.Day18
import com.aoc2022.day.Day19
import com.aoc2022.day.GenericDay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.io.File

class Day19Test {

    @Test
    fun pt1_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(33, tut.solve1(EXAMPLE_INPUT))
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(FILE_INPUT)
        println(res)
        assertNotEquals(1711, res) // too low
        assertNotEquals(1708, res) // too low
        assertEquals(1719, res)
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(56 * 62, tut.solve2(EXAMPLE_INPUT))
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(FILE_INPUT)
        println(res)
        assertEquals(30 * 21 * 31, res)
    }

    companion object {
        private val DAY_CLASS = Day19::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }

        fun tutInstance(): GenericDay {
            return DAY_CLASS.newInstance()
        }

        val EXAMPLE_INPUT = """
Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
        """.trimIndent().lines()
    }
}