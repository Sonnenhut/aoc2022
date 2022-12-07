package com.aoc2022.day

class Day1 : GenericDay() {

    private fun summarize(lines : List<String>) : List<Int> {
        var blocks = ArrayList<Int>()
        var sum = 0
        for (line in lines) {
            val trimmed = line.trim()
            if (trimmed.isEmpty()) {
                blocks.add(sum)
                sum = 0
            } else {
                sum += trimmed.toInt()
            }
        }
        blocks.add(sum)
        return blocks
    }

    override fun solve1(input : List<String>): Int {
        return summarize(input).maxOf { it }
    }

    override fun solve2(input : List<String>): Int {
        return summarize(input).sortedDescending().subList(0,3).sum()
    }
}