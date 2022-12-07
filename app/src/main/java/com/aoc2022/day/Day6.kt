package com.aoc2022.day

class Day6 : GenericDay() {

    override fun solve1(input: List<String>): Int {
        val buffer = input[0]
        return markerIdxAt(buffer, 4)
    }

    private fun markerIdxAt(buffer: String, markerSize: Int): Int {
        var res = -1
        for (startIdx in buffer.indices) {
            val received = buffer.substring(startIdx, startIdx + markerSize)
            if (received.toSet().size == markerSize) { // received chars need to be unique
                res = startIdx + received.length
                break
            }
        }
        return res
    }

    override fun solve2(input: List<String>): Int {
        val buffer = input[0]
        return markerIdxAt(buffer, 14)
    }

}
