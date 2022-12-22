package com.aoc2022.day

import kotlin.math.max

class Day17 : GenericDay() {

    override fun solve1(input: List<String>): Long {
        return solve(input, 2022L)
    }

    override fun solve2(input: List<String>): Long {
        return solve(input, 1000000000000L)
    }

    fun solve(input: List<String>, atFormation: Long) : Long {
        val rockTypes = rocks().iterator()
        val down = Pair(0, -1)
        val downMoves: Sequence<Pair<Int, Int>> = sequenceOf(down).repeat()
        val moves: Iterator<Pair<Int, Int>> =
            offsets(input).zip(downMoves).flatMap { (a, b) -> listOf(a, b).asSequence() }.iterator()

        val yToRockNum: MutableMap<Int, Int> = mutableMapOf()

        var highestRockY = 0
        val settledFormations = mutableListOf<Formation>()
        val settledPoints = mutableSetOf<Pair<Int, Int>>()

        for (idx in 1..20000) {
            var rock = rockTypes.next()
            //val highestRockY = settled.points.maxOfOrNull { it.second } ?: 0
            // keep track of which rock was responsible for which y height
            yToRockNum[highestRockY] = idx - 1

            rock = rock.withOffset(Pair(3, highestRockY + 4))

            do {
                val move = moves.next()
                val newRock = rock.withOffset(move)
                val touchesSomething =  newRock.touchesWallOrGround() || newRock.points.any { settledPoints.contains(it) }

                if (!touchesSomething) {
                    rock = newRock
                }
            } while (!(touchesSomething && move == down))

            settledFormations.add(rock)
            settledPoints.addAll(rock.points)
            highestRockY = max(highestRockY, rock.points.maxOf { it.second })

        }

        val settledByRow: Map<Int, Set<Int>> = settledFormations.flatMap { it.points }.groupBy { it.second }.mapValues { entry -> entry.value.map { it.first }.toSet() }


        val maxY = settledByRow.keys.max()
        var recurringPattern: List<Set<Int>>? = null
        var yOffset: Int = -1

        var y = 0
        while (recurringPattern == null && y <= maxY) {
            val sourceRow = settledByRow[y]

            // look further in the distance, if the pattern between y and csrY is repeating...
            for (csrY in (y + 30)..maxY) {
                val otherRow = settledByRow[csrY]
                if (otherRow == sourceRow) {
                    val isRecurring = settledByRow.rangesSame(y, csrY)
                    if (isRecurring) {
                        recurringPattern = (y until csrY).map { settledByRow[it]!! }
                        yOffset = y
                        break
                    }
                }
            }

            y += 1
        }

        val firstFormationNumAtY : (Int) -> Int = {yCoord -> settledFormations.indexOfFirst { formation ->
            formation.points.map { it.second }.contains(yCoord)
        } + 1 }

        val formationOffset = firstFormationNumAtY(yOffset)

        //val formationOffset = yToRockNum[yOffset]!!
        val patternYsize = recurringPattern!!.size
        val patternFormationCnt = firstFormationNumAtY(yOffset + patternYsize) - formationOffset

        val wanted = atFormation
        val wantedNoOffset = (wanted - formationOffset)
        val fittingPatterns = wantedNoOffset / patternFormationCnt
        val restFormations = (wantedNoOffset - (fittingPatterns * patternFormationCnt)).toInt()
        val restY =
            yToRockNum.entries.find { it.value == formationOffset + restFormations }!!.key - yOffset
        val r: Long = yOffset + (fittingPatterns * patternYsize) + restY

        return r
    }

    fun Map<Int, Set<Int>>.rangesSame(yStart1 : Int, yStart2 : Int) : Boolean {
        var res = true
        val windowSize = yStart2 - yStart1 //  (y until csrY).count()

        for(csr in yStart1 until yStart2) {
            if(this[yStart2] == null || this[yStart1] == null) {
                throw IllegalArgumentException("Trying to compare out of range...")
            }
            if(this[csr] != this[csr + windowSize]) {
                res = false
                break
            }
        }
        return res
    }

    private fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }

    private fun offsets(line: List<String>): Sequence<Pair<Int, Int>> {
        return line[0].map {
            when (it) {
                '<' -> Pair(-1, 0)
                '>' -> Pair(1, 0)
                else -> throw IllegalArgumentException("Unable to parse offset $it.")
            }
        }.asSequence().repeat()
    }

    private fun rocks(): Sequence<Formation> {
        return listOf(
            "####",
            """
        .#.
        ###
        .#.
        """,

            """
        ..#
        ..#
        ###
        """,

            """
        #
        #
        #
        #
        """,
            """
        ##
        ##
        """
        ).map { Formation.parse(it.trimIndent().lines()) }.asSequence().repeat()
    }

    class Formation(val points: Set<Pair<Int, Int>>) {

        fun withOffset(off: Pair<Int, Int>): Formation {
            return Formation(points.map { it + off }.toSet())
        }

        fun touches(other: Formation): Boolean {
            return this.points.intersect(other.points).isNotEmpty()
        }

        fun touchesWallOrGround(): Boolean {
            return this.points.any { it.first == 0 || it.first == 8 || it.second == 0 }
        }

        operator fun plus(other: Formation): Formation {
            return Formation(this.points.union(other.points))
        }

        companion object {
            fun parse(lines: List<String>): Formation {
                val parse = lines.reversed()
                val points = mutableSetOf<Pair<Int, Int>>()
                for (x in lines[0].indices) {
                    for (y in lines.indices) {
                        if (parse[y][x] == '#') {
                            points.add(Pair(x, y))
                        }
                    }
                }
                return Formation(points)
            }
        }
    }
}


internal operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first + other.first, this.second + other.second)
}