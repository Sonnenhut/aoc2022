package com.aoc2022

import com.aoc2022.day.Day17
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.io.File

class Day17Test {

    @Test
    fun pt1_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(3068L, tut.solve1(EXAMPLE_INPUT))
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(FILE_INPUT)
        println(res)
        assertEquals(3197L, res)
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(1514285714288L, tut.solve2(EXAMPLE_INPUT))
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(FILE_INPUT)
        println(res)
        assertNotEquals(1799999999766L, res) // too high
        assertEquals(1568513119571L, res)
    }

    companion object {
        private val DAY_CLASS = Day17::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }

        fun tutInstance(): Day17 {
            return DAY_CLASS.newInstance()
        }

        val EXAMPLE_INPUT = """
>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>
        """.trimIndent().lines()
    }
}