package com.aoc2022.day

class Day4 : GenericDay() {
    override fun solve1(input: List<String>): Int {
        var count = 0
        for (line in input) {
          if (isFullOverlapping(line)) {
              count += 1
          }
        }
        return count
    }

    fun isFullOverlapping(line : String) : Boolean {
        var overlapping = false

        val (elve1, elve2) = line.split(",")
        val (e1start, e1end) = elve1.split("-").map { it.toInt() }
        val (e2start, e2end) = elve2.split("-").map { it.toInt() }

        if (e1start <= e2start && e1end >= e2end) {
            overlapping = true
        } else if (e2start <= e1start && e2end >= e1end) {
            overlapping = true
        }
        return overlapping
    }

    override fun solve2(input: List<String>): Int {
        var count = 0
        for (line in input) {
            if(overlaps(line)) {
                count += 1
            }
        }

        return count
    }

    private fun overlaps(line: String): Boolean {
        val (elve1, elve2) = line.split(",")
        val (e1start, e1end) = elve1.split("-").map { it.toInt() }
        val (e2start, e2end) = elve2.split("-").map { it.toInt() }

        val e1Nums = (e1start..e1end).toSet()
        val e2Nums = (e2start..e2end).toSet()

        return e1Nums.intersect(e2Nums).isNotEmpty()
    }
}
