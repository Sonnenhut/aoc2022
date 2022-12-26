package com.aoc2022

import com.aoc2022.day.Day25
import com.aoc2022.day.GenericDay
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day25Test {

    @Test
    fun pt1_simpleSNAFU_isCorrect() {
        val tut = Day25()
        assertEquals(100L, tut.fromSNAFUtoLong("1-00"))
        assertEquals(75L, tut.fromSNAFUtoLong("1=00"))

        assertEquals(1L, tut.fromSNAFUtoLong("1"))
        assertEquals(2L, tut.fromSNAFUtoLong("2"))
        assertEquals(3L, tut.fromSNAFUtoLong("1="))
        assertEquals(4L, tut.fromSNAFUtoLong("1-"))
        assertEquals(5L, tut.fromSNAFUtoLong("10"))
        assertEquals(6L, tut.fromSNAFUtoLong("11"))
        assertEquals(7L, tut.fromSNAFUtoLong("12"))
        assertEquals(8L, tut.fromSNAFUtoLong("2="))
        assertEquals(9L, tut.fromSNAFUtoLong("2-"))
        assertEquals(10L, tut.fromSNAFUtoLong("20"))
        assertEquals(15L, tut.fromSNAFUtoLong("1=0"))
        assertEquals(20L, tut.fromSNAFUtoLong("1-0"))
        assertEquals(2022L, tut.fromSNAFUtoLong("1=11-2"))
        assertEquals(12345L, tut.fromSNAFUtoLong("1-0---0"))
        assertEquals(314159265L, tut.fromSNAFUtoLong("1121-1110-1=0"))
    }

    @Test
    fun pt1_SNAFUtoLong_isCorrect() {
        val tut = Day25()
        assertEquals("1-00", tut.fromLongToSNAFU(100L))
        assertEquals("1=00", tut.fromLongToSNAFU(75L))


        assertEquals( "1", tut.fromLongToSNAFU(1L))
        assertEquals( "2", tut.fromLongToSNAFU(2L))
        assertEquals( "1=", tut.fromLongToSNAFU(3L))
        assertEquals( "1-", tut.fromLongToSNAFU(4L))
        assertEquals( "10", tut.fromLongToSNAFU(5L))
        assertEquals( "11", tut.fromLongToSNAFU(6L))
        assertEquals( "12", tut.fromLongToSNAFU(7L))
        assertEquals( "2=", tut.fromLongToSNAFU(8L))
        assertEquals( "2-", tut.fromLongToSNAFU(9L))
        assertEquals( "20", tut.fromLongToSNAFU(10L))
        assertEquals( "1=0", tut.fromLongToSNAFU(15L))
        assertEquals( "1-0", tut.fromLongToSNAFU(20L))
        assertEquals( "1=11-2", tut.fromLongToSNAFU(2022L))
        assertEquals( "1-0---0", tut.fromLongToSNAFU(12345L))
        assertEquals( "1121-1110-1=0", tut.fromLongToSNAFU(314159265L))

    }

    @Test
    fun pt1_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals("2=-1=0", tut.solve1(EXAMPLE_INPUT))
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(FILE_INPUT)
        println(res)
        assertEquals("122-12==0-01=00-0=02", res)
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
        private val DAY_CLASS = Day25::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }

        fun tutInstance(): GenericDay {
            return DAY_CLASS.newInstance()
        }

        val EXAMPLE_INPUT = """
1=-0-2
 12111
  2=0=
    21
  2=01
   111
 20012
   112
 1=-1=
  1-12
    12
    1=
   122
        """.trimIndent().lines()
    }
}