package com.aoc2022.day

class Day3 : GenericDay() {
    override fun solve1(input: List<String>): Int {
        var prioSum = 0
        for (rucksack in input) {
            val middleIdx = rucksack.length / 2
            val comp1 = rucksack.substring(0, middleIdx)
            val comp2 = rucksack.substring(middleIdx)

            val uniqueComp1 = comp1.toSet()
            val uniqueComp2 = comp2.toSet()

            val intersection = uniqueComp1.intersect(uniqueComp2)
            val priority = calcPriority(intersection.first())
            prioSum += priority
        }
        return prioSum
    }

    override fun solve2(input: List<String>): Int {
        return input.chunked(3)
            .sumOf { group ->
                val intersection: Set<Char> = group.map { rucksack -> rucksack.toSet() }
                    .reduce { set1, set2 -> set1.intersect(set2) }
                calcPriority(intersection.first())
            }
    }

    private fun calcPriority(char: Char): Int {
        return if (char.isLowerCase()) {
            char - 'a' + 1
        } else {
            char - 'A' + 27
        }
    }
}
