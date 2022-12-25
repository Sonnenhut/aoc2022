package com.aoc2022.day

import kotlin.streams.toList

class Day8 : GenericDay() {
    override fun solve1(input: List<String>): Int {
        val grid: List<List<Int>> = input.map { line -> line.chars().map { it - '0'.code }.toList() }

        var cnt = 0
        for (y in grid.indices) {
            for (x in grid.indices) {
                listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))
                    .find { grid.isVisibleFrom(Pair(x,y), it) }
                    ?.let {
                        cnt += 1
                    }
            }
        }

        return cnt
    }

    override fun solve2(input: List<String>): Int {
        val grid: List<List<Int>> = input.map { line -> line.chars().map { it - '0'.code }.toList() }

        var max = 0
        for (y in grid.indices) {
            for (x in grid.indices) {
                val sum = listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))
                    .map { grid.countVision(Pair(x, y), it).second }
                    .reduce { left, right -> left * right }
                if (sum > max) {
                    max = sum
                }
            }
        }

        return max
    }

    fun List<List<Int>>.countVision(
        coords: Pair<Int, Int>,
        off: Pair<Int, Int>
    ): Pair<Boolean, Int> {
        var freeView = true
        var viewCnt = 0
        val size = this.at(coords)
        var csr = coords + off

        while (freeView && this.isCoordValid(csr)) {
            freeView = freeView && size > this.at(csr)
            csr += off
            viewCnt += 1
        }

        return Pair(freeView, viewCnt)
    }

    fun List<List<Int>>.isVisibleFrom(coords: Pair<Int, Int>, off: Pair<Int, Int>): Boolean {
        return this.countVision(coords, off).first
    }

    fun List<List<Int>>.at(coord: Pair<Int, Int>): Int {
        return this[coord.second][coord.first]
    }

    fun List<List<Int>>.isCoordValid(coord: Pair<Int, Int>): Boolean {
        val validRange = 0..this.indices.max()
        return coord.first in validRange && coord.second in validRange
    }

}

