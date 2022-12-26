package com.aoc2022.day

import kotlin.math.pow

class Day25 : GenericDay() {

    override fun solve1(input: List<String>): String {
        return fromLongToSNAFU(input.sumOf { fromSNAFUtoLong(it) })
    }

    override fun solve2(input: List<String>): Int {
        return 0
    }

    fun fromLongToSNAFU(num : Long) : String {
        val numStr = num.toString(5).reversed()
        val res = mutableMapOf<Int, Int>()

        for((idx, char) in numStr.withIndex()) {
            val newDigit = char.digitToInt() + res.getOrDefault(idx, 0)
            if(newDigit == 5) {
                res[idx + 1] = res.getOrDefault(idx + 1, 0) + 1
                res[idx] = 0
            } else if(newDigit == 4) {
                res[idx + 1] = res.getOrDefault(idx + 1, 0) + 1
                res[idx] = -1
            } else if(newDigit == 3) {
                res[idx + 1] = res.getOrDefault(idx + 1, 0) + 1
                res[idx] = -2
            } else {
                res[idx] = newDigit
            }
        }
        return res.entries.sortedByDescending { it.key }.joinToString("") {
            when (it.value) {
                -1 -> "-"
                -2 -> "="
                else -> it.value.toString()
            }
        }
    }

    fun fromSNAFUtoLong(str : String) : Long {
        return str.trim().reversed().withIndex().sumOf { (idx, char) ->
            val num = when (char) {
                '-' -> -1
                '=' -> -2
                else -> char.digitToInt()
            }
            val potency = 5.0.pow(idx)
            num * potency
        }.toLong()
    }
}
