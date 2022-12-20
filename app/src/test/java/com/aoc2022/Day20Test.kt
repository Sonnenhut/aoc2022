package com.aoc2022

import com.aoc2022.day.Day20
import com.aoc2022.day.GenericDay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.io.File

class Day20Test {

    @Test
    fun pt1_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(3L, tut.solve1(EXAMPLE_INPUT))
    }
    @Test
    fun pt1_minusOne_isCorrect() {
        val initial = "5,4,3,2,-1,2,3,4,5,0".replace(",","\n").trimIndent().lines()

        val edge = Day20.Edge.parse(initial)[4]
        edge.move()
        assertEquals(listOf(-1,2,2,3,4,5,0,5,4,3).toString(), edge.toString())
    }

    @Test
    fun pt1_minusToTheSamePlace_isCorrect() {
        val initial = "5,4,3,2,-9,2,3,4,5,0".replace(",","\n").trimIndent().lines()

        val edge = Day20.Edge.parse(initial)[4]
        edge.move()
        assertEquals(listOf(-9,2,3,4,5,0,5,4,3,2).toString(),edge.toString())
    }

    @Test
    fun pt1_minusOnceOver_isCorrect() { // should be same as -1
        val initial = "5,4,3,2,-10,2,3,4,5,0".replace(",","\n").trimIndent().lines()

        val edge = Day20.Edge.parse(initial)[4]
        edge.move()
        assertEquals(listOf(-10,2,2,3,4,5,0,5,4,3).toString(), edge.toString())
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(FILE_INPUT)
        println(res)
        assertNotEquals(-6555L, res)
        assertNotEquals(6555L, res) // too low
        assertEquals(7584L, res)
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(1623178306L, tut.solve2(EXAMPLE_INPUT))
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve2(FILE_INPUT)
        println(res)
        assertEquals(4907679608191L, res)
    }

    companion object {
        private val DAY_CLASS = Day20::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }

        fun tutInstance(): GenericDay {
            return DAY_CLASS.newInstance()
        }

        val EXAMPLE_INPUT = """
            1
            2
            -3
            3
            -2
            0
            4
        """.trimIndent().lines()
    }
}