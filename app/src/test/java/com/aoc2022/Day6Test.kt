package com.aoc2022

import com.aoc2022.day.Day6
import com.aoc2022.day.GenericDay
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day6Test {

    @Test
    fun pt1_exampleInput1_isCorrect() {
        val tut = tutInstance()
        assertEquals(7, tut.solve1(EXAMPLE_INPUT_1))
        assertEquals(5, tut.solve1(EXAMPLE_INPUT_2))
        assertEquals(6, tut.solve1(EXAMPLE_INPUT_3))
        assertEquals(10, tut.solve1(EXAMPLE_INPUT_4))
        assertEquals(11, tut.solve1(EXAMPLE_INPUT_5))
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(FILE_INPUT)
        assertEquals(1929, res)
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(19, tut.solve2(EXAMPLE_INPUT_1))
        assertEquals(23, tut.solve2(EXAMPLE_INPUT_2))
        assertEquals(23, tut.solve2(EXAMPLE_INPUT_3))
        assertEquals(29, tut.solve2(EXAMPLE_INPUT_4))
        assertEquals(26, tut.solve2(EXAMPLE_INPUT_5))
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(FILE_INPUT)
        assertEquals(3298, res)
    }

    companion object {
        private val DAY_CLASS = Day6::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }

        fun tutInstance(): GenericDay {
            return DAY_CLASS.newInstance()
        }

        val EXAMPLE_INPUT_1 = listOf("mjqjpqmgbljsphdztnvjfqwrcgsmlb")
        val EXAMPLE_INPUT_2 = listOf("bvwbjplbgvbhsrlpgdmjqwftvncz")
        val EXAMPLE_INPUT_3 = listOf("nppdvjthqldpwncqszvftbrmjlhg")
        val EXAMPLE_INPUT_4 = listOf("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")
        val EXAMPLE_INPUT_5 = listOf("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")
    }
}