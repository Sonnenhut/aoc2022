package com.aoc2022

import com.aoc2022.day.Day13
import com.aoc2022.day.GenericDay
import org.junit.Assert.*
import org.junit.Test
import java.io.File

class Day13Test {

    @Test
    fun pt1_examplesCompareTo_isCorrect() {
        val d = Day13()
        val p : (String) -> List<Any> = d::parse
        assertEquals(-1, d.compare(p("[1,1,3,1,1]"), p("[1,1,5,1,1]")))
        assertEquals(-1, d.compare(p("[[1],[2,3,4]]"), p("[[1],4]")))
        assertEquals(1, d.compare(p("[9]"), p("[[8,7,6]]")))
        assertEquals(-1, d.compare(p("[[4,4],4,4]"), p("[[4,4],4,4,4]")))
        assertEquals(1, d.compare(p("[7,7,7,7]"), p("[7,7,7]")))
        assertEquals(-1, d.compare(p("[]"), p("[3]")))
        assertEquals(1, d.compare(p("[[[]]]"), p("[[]]")))
        assertEquals(1, d.compare(p("[1,[2,[3,[4,[5,6,7]]]],8,9]"), p("[1,[2,[3,[4,[5,6,0]]]],8,9]")))
    }


    @Test
    fun pt1_customExamples_isCorrect() {
        val d = Day13()
        val p : (String) -> List<Any> = d::parse
        assertEquals(-1, d.compare(p("[1,[1,2],3]"), p("[1,[1,2,3],3]")))
        assertEquals(-1, d.compare(p("[1,[1,2,[1]]]"), p("[1,[1,2,[1],3]]")))
        assertEquals(1, d.compare(p("[1,[1,2,[1]],1,1]"), p("[1,[1,2,[1]],[1]]"))) // right is longer
        assertEquals(0, d.compare(p("[1,[1,2,[1]],1,1]"), p("[1,[1,2,[1]],[1],[1]]")))

        // integer checking, eq, gt, lt
        assertEquals(0, d.compare(p("[1]"), p("[1]")))
        assertEquals(-1, d.compare(p("[0]"), p("[1]")))
        assertEquals(1, d.compare(p("[1]"), p("[0]")))

        //left list is smaller, thus less than
        assertEquals(0, d.compare(p("[1,2,3]"), p("[1,2,3]")))
        assertEquals(-1, d.compare(p("[1,2]"), p("[1,2,3]")))
        assertEquals(1, d.compare(p("[1,2,3]"), p("[1,2]")))
        assertEquals(-1, d.compare(p("[1,[1,2],9]]"), p("[1,[1,2,9],0]"))) // higher because of 9
        assertEquals(-1, d.compare(p("[1,[1,2],0]]"), p("[1,[1,2,9],9]")))
        assertEquals(-1, d.compare(p("[1,[1,2],0]]"), p("[1,[1,2,9],9]")))
        assertEquals(-1, d.compare(p("[1,[1,2]]]"), p("[1,[1,2],9]")))
        assertEquals(1, d.compare(p("[1,[1,2],9]]"), p("[1,[1,2]]")))
        assertEquals(-1, d.compare(p("[1,1,9]]"), p("[1,[2,3],9]")))

        // !!! 2 vs [2,3]. left should win
        assertEquals(-1, d.compare(p("[1,[2],9]]"), p("[1,[2,3],9]")))
        assertEquals(-1, d.compare(p("[1,2,9]]"), p("[1,[2,3],9]")))
    }

    @Test
    fun pt1_exampleInput1_isCorrect() {
        val tut = tutInstance()
        assertEquals(13, tut.solve1(EXAMPLE_INPUT))
    }

    @Test
    fun pt1_myInput_isCorrect() {
        val tut = tutInstance()
        val res = tut.solve1(FILE_INPUT)
        println(res)
        assertNotEquals(10092, res) // too high
        assertNotEquals(5702, res) // too low
        assertNotEquals(6155, res) // too low
        assertNotEquals(5921, res) // not right
        assertNotEquals(5676, res) // not right
        assertNotEquals(4347, res) // not right
        assertEquals(6235, res)
    }

    @Test
    fun pt2_exampleInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(140, tut.solve2(EXAMPLE_INPUT))
    }

    @Test
    fun pt2_myInput_isCorrect() {
        val tut = tutInstance()
        assertEquals(22866, tut.solve2(FILE_INPUT))
    }

    companion object {
        private val DAY_CLASS = Day13::class.java
        val FILE_INPUT by lazy {
            val classLower = DAY_CLASS.simpleName.lowercase() // e.g. day1
            File("src/main/res/raw/$classLower.txt").readLines()
        }

        fun tutInstance(): GenericDay {
            return DAY_CLASS.newInstance()
        }

        val EXAMPLE_INPUT = """
[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]
        """.trimIndent().lines()
    }
}