package com.aoc2022.day

import kotlin.math.min

class Day13 : GenericDay() {
    override fun solve1(input: List<String>): Int {
        val chunked: List<Pair<List<Any>, List<Any>>> =
            input.chunked(3).map { Pair(parse(it[0]), parse(it[1])) }

        val res = chunked.mapIndexed { idx, (left, right) ->
            if (compare(left, right) == -1) {
                idx + 1
            } else {
                0
            }
        }.sum()

        return res
    }

    override fun solve2(input: List<String>): Int {
        val dividerPackets = listOf(parse("[[2]]"), parse("[[6]]"))
        val parsedInput: List<List<Any>> =
            input.filter { it.isNotBlank() }
                .map { parse(it) }

        val sorted = parsedInput.union(dividerPackets).toList().sortedWith{ a, b -> compare(a, b)}
        return dividerPackets.map { sorted.indexOf(it) + 1 }.reduce{ a,b -> a*b }
    }

    fun parse(str: String): List<Any> {
        val res = mutableListOf<Any>()
        _parse(str, res)
        return res
    }

    private fun _parse(str: String, res: MutableList<Any>, initCsr: Int = 0): Int {
        var idx = initCsr
        while (idx in str.indices) {
            when (val char = str[idx]) {
                '[' -> {
                    val innerList = mutableListOf<Any>()
                    res.add(innerList)
                    idx = _parse(str, innerList, idx + 1)
                }
                ',' -> {}
                ' ' -> {}
                ']' -> return idx
                else -> {
                    if(!char.isDigit()) {
                        throw IllegalArgumentException("Unable to parse text, invalid char '$char'")
                    }
                    // lookahead to the next comma
                    val numberStr = str.substring(idx, str.length).takeWhile { it.isDigit() }
                    res.add(numberStr.toInt())
                    idx += numberStr.length -1
                }
            }
            idx++
        }
        return idx
    }

    fun compare(leftList: List<*>, rightList: List<*>): Int {
        var res = 0

        val maxIdx = min(leftList.size, rightList.size)
        for (idx in 0 until maxIdx) {
            var left = leftList[idx]
            var right = rightList[idx]

            // always compare a list to another list
            if (left is Int && right is List<*>) {
                left = listOf(left)

            } else if (left is List<*> && right is Int) {
                right = listOf(right)
            }

            // start comparing
            if (left is Int && right is Int) {
                res = left.compareTo(right)

            } else if (left is List<*> && right is List<*>) {
                res = compare(left, right)
                if (left.size != right.size && res == 0) {
                    res = left.size.compareTo(right.size)
                }

            }
            if(res != 0) {
                break
            }
        }

        return res
    }
}

