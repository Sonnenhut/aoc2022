package com.aoc2022.day

class Day5 : GenericDay() {

    override fun solve1(input: List<String>): String {
        val stacks : List<MutableList<Char>> = parseStack(input)
        val comms = parseCommands(input)

        for (command in comms) {
            val (amt, fromIdx, toIdx) = command
            (0 until amt).forEach { _ -> stacks[toIdx].add(stacks[fromIdx].removeLast()) }
        }
        return stacks.map { it.last() }.joinToString("")
    }

    override fun solve2(input: List<String>): String {
        val stacks : List<MutableList<Char>> = parseStack(input)
        val comms = parseCommands(input)

        for (command in comms) {
            val (amt, fromIdx, toIdx) = command
            val from = stacks[fromIdx]
            val to = stacks[toIdx]

            val removeAtIdx = from.size - amt
            val toAdd = (0 until amt).map { from.removeAt(removeAtIdx) }
            to.addAll(toAdd)
        }
        return stacks.map { it.last() }.joinToString("")
    }

    private fun parseStack(input: List<String>): List<MutableList<Char>> {
        val delimiterIdx = input.indexOf("")
        val stackDesc = input.subList(0, delimiterIdx - 1)
        val stackCnt = input[delimiterIdx - 1].split("").filter { it.isNotBlank() }
            .maxOfOrNull { it.toInt() }
        val res: List<MutableList<Char>> = (0 until stackCnt!!).map { ArrayList<Char>() }.toList()
        for (line in stackDesc) {
            res.forEachIndexed { idx, charStack: MutableList<Char> ->
                line.getOrNull(idx * 4 + 1)?.let {
                    if (it != ' ') {
                        charStack.add(0, it)
                    }
                }
            }
        }
        return res
    }

    private fun parseCommands(input: List<String>): List<Triple<Int, Int, Int>> {
        val delimiterIdx = input.indexOf("")
        val commandsStr = input.subList(delimiterIdx + 1, input.size)

        //move 1 from 2 to 1
        //move 15 from 3 to 4
        val elems : List<List<String>> = commandsStr.map { it.split(" ") }
        return elems.map { Triple(it[1].toInt(), it[3].toInt() - 1, it[5].toInt() - 1) }
    }
}
