package com.aoc2022

import com.aoc2022.day.Day3
import com.aoc2022.day.GenericDay
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day3Test {

    @Test
    fun pt1_exampleInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(EXAMPLE_INPUT)
        assertEquals(157, res)
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val myInput = FILE_INPUT
        val res = tut.solve1(myInput)
        assertEquals(8349, res)
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(EXAMPLE_INPUT)
        assertEquals(70, res)
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(FILE_INPUT)
        assertEquals(2681, res)
    }

    companion object {
        private val DAY_CLASS = Day3::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }

        fun tutInstance(): GenericDay {
            return DAY_CLASS.newInstance()
        }

        val EXAMPLE_INPUT = """
vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent().lines()
    }
}