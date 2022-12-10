package com.aoc2022.day

class Day10 : GenericDay() {
    override fun solve1(input: List<String>): Int {
        var strength = 0
        val values = parseInstructions(input)

        var x = 1
        for ((idx, value) in values.withIndex()) {
            val cycle = idx + 1
            if (cycle in 20..values.size step 40) {
                strength += cycle * x
            }
            x += value
        }
        return strength
    }

    private fun parseInstructions(input: List<String>): List<Int> {
        val paramIdx = "addx ".length
        val values = input
            .flatMap {
                if (it == "noop") {
                    listOf(0)
                } else {
                    listOf(0, it.substring(paramIdx).toInt())
                }
            }
        return values
    }

    override fun solve2(input: List<String>): List<String> {
        val values = parseInstructions(input)
        val lines: MutableList<String> = mutableListOf()
        var buffer = ""

        var x = 1
        for ((idx, value) in values.withIndex()) {
            val spriteOverlay = x - 1..x + 1
            val pxlPos = idx % 40

            buffer += if (pxlPos in spriteOverlay) {
                "#"
            } else {
                "."
            }
            if (buffer.length == 40) {
                lines.add(buffer)
                buffer = ""
            }

            x += value
        }
        return lines
    }

}

