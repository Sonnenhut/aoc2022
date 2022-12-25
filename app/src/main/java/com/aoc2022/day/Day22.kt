package com.aoc2022.day

import com.aoc2022.day.Day22.Facing.*

class Day22 : GenericDay() {

    override fun solve1(input: List<String>): Int {
        val instructions =
            input.last().replace("R", " R ").replace("L", " L ").split(" ").toMutableList()
        val face = parsePoints(input)
        val wall = face.wall
        val floor = face.floor
        val world = face.world

        var csr = floor.filter { it.second == 0 }.minBy { it.first }
        var facing = RIGHT

        while (instructions.isNotEmpty()) {
            val instr = instructions.removeAt(0)
            if (instr.toIntOrNull() == null) {
                facing = facing.turn(instr)
                continue
            }
            var amt = instr.toInt()

            while (amt != 0) {
                var nextCsr = csr + facing.off
                if (!world.contains(nextCsr)) {
                    when (facing) {
                        RIGHT -> {
                            val newX = world.filter { it.second == csr.second }.minOf { it.first }
                            nextCsr = Pair(newX, csr.second)

                        }
                        LEFT -> {
                            val newX = world.filter { it.second == csr.second }.maxOf { it.first }
                            nextCsr = Pair(newX, csr.second)

                        }
                        UP -> {
                            val newY = world.filter { it.first == csr.first }.maxOf { it.second }
                            nextCsr = Pair(csr.first, newY)

                        }
                        DOWN -> {
                            val newY = world.filter { it.first == csr.first }.minOf { it.second }
                            nextCsr = Pair(csr.first, newY)

                        }
                    }
                }
                if (wall.contains(nextCsr)) {
                    break // hit wall, cannot iterate more..
                }
                csr = nextCsr
                amt -= 1
            }
        }

        return calcPassword(csr, facing)
    }

    fun calcPassword(coord: Pair<Int, Int>, face : Facing) : Int {

        val row = coord.second + 1
        val col = coord.first + 1
        val faceVal = Facing.values().indexOf(face)

        return 1000 * row + 4 * col + faceVal
    }

    private fun parsePoints(input: List<String>): Face {
        val wall = mutableSetOf<Pair<Int, Int>>()
        val floor = mutableSetOf<Pair<Int, Int>>()
        val world = mutableSetOf<Pair<Int, Int>>()

        input.withIndex().forEach { (y, row) ->
            row.withIndex()
                .forEach { (x, char) ->
                    if ('#' == char) {
                        wall.add(Pair(x, y))
                        world.add(Pair(x, y))
                    } else if ('.' == char) {
                        floor.add(Pair(x, y))
                        world.add(Pair(x, y))
                    }
                }
        }
        return Face(wall, floor)
    }

    fun solve2Example(input: List<String>) : Int {
        val wraps = pt2exampleWraps(input)
        return solve2(input, wraps)
    }

    fun solve2(input: List<String>, wraps : List<WrapInstr>) : Int{
        val instructions =
            input.last().replace("R", " R ").replace("L", " L ").split(" ").toMutableList()
        val face = parsePoints(input)
        val wall = face.wall
        val floor = face.floor
        val world = face.world


        var csr = floor.filter { it.second == 0 }.minBy { it.first }
        var facing = RIGHT

        while (instructions.isNotEmpty()) {
            val instr = instructions.removeAt(0)
            if (instr.toIntOrNull() == null) {
                facing = facing.turn(instr)
                continue
            }
            var amt = instr.toInt()

            while (amt != 0) {
                var nextCsr = csr + facing.off
                var nextFacing = facing
                if (!world.contains(nextCsr)) {
                    val wrapInstr = wraps.filter { it.fromFace == facing }.firstOrNull { it.from.contains(csr) }
                    val (wrappedFacing, wrappedCsr) = wrapInstr!!.wrap(facing, csr)!!
                    nextFacing = wrappedFacing
                    nextCsr = wrappedCsr
                }
                if (wall.contains(nextCsr)) {
                    break // hit wall, cannot iterate more..
                }
                csr = nextCsr
                facing = nextFacing
                //println(csr)
                amt -= 1
            }
        }

        return calcPassword(csr, facing)
    }

    override fun solve2(input: List<String>): Int {
        val wraps = pt2wraps(input)
        return solve2(input, wraps)
    }

    // hardcoded wrappings...
    fun pt2wraps(input : List<String>): List<WrapInstr> {
        val dim = 50

        val behindOff = Pair(50, 0)
        val rightOff = Pair(100, 0)
        val topOff = Pair(50, 50)
        val leftOff = Pair(0, 100)
        val frontOff = Pair(50, 100)
        val bottomOff = Pair(0, 150)

        val allFace = parsePoints(input)

        val top = allFace.inArea(topOff, dim)
        val behind = allFace.inArea(behindOff, dim)
        val left = allFace.inArea(leftOff, dim)
        val front = allFace.inArea(frontOff, dim)
        val bottom = allFace.inArea(bottomOff, dim)
        val right = allFace.inArea(rightOff, dim)

        val zips = listOf(
            WrapInstr(behind.topEdge, bottom.leftEdge, UP, RIGHT),
            WrapInstr(behind.leftEdge, left.leftEdge.reversed(), LEFT, RIGHT),
            WrapInstr(right.topEdge, bottom.bottomEdge, UP, UP),
            WrapInstr(right.rightEdge, front.rightEdge.reversed(), RIGHT, LEFT),
            WrapInstr(right.bottomEdge, top.rightEdge, DOWN, LEFT),
            WrapInstr(top.leftEdge, left.topEdge, LEFT, DOWN),
            WrapInstr(front.bottomEdge, bottom.rightEdge, DOWN, LEFT)
        )
        return zips + zips.map { it.inverse() }
    }

    fun pt2exampleWraps(input : List<String>): List<WrapInstr> {
        val dim = 4
        val topOff = Pair(8, 0)
        val behindOff = Pair(0, 4)
        val leftOff = Pair(4, 4)
        val frontOff = Pair(8, 4)
        val bottomOff = Pair(8, 8)
        val rightOff = Pair(12, 8)

        val allFace = parsePoints(input)

        val top = allFace.inArea(topOff, dim)
        val behind = allFace.inArea(behindOff, dim)
        val left = allFace.inArea(leftOff, dim)
        val front = allFace.inArea(frontOff, dim)
        val bottom = allFace.inArea(bottomOff, dim)
        val right = allFace.inArea(rightOff, dim)

        val zips = listOf(
            WrapInstr(top.topEdge, behind.topEdge.reversed(), UP, DOWN),
            WrapInstr(top.leftEdge, left.topEdge, LEFT, DOWN),
            WrapInstr(top.rightEdge, right.rightEdge.reversed(), RIGHT, LEFT),
            WrapInstr(front.rightEdge, right.topEdge.reversed(), RIGHT, DOWN),
            WrapInstr(left.bottomEdge, bottom.leftEdge.reversed(), DOWN, RIGHT),
            WrapInstr(behind.leftEdge, right.bottomEdge.reversed(), LEFT, UP),
            WrapInstr(behind.bottomEdge, bottom.bottomEdge.reversed(), DOWN, UP)
        )
        return zips + zips.map { it.inverse() }
    }

    class WrapInstr(
        val from: List<Pair<Int, Int>>,
        val to: List<Pair<Int, Int>>,
        val fromFace: Facing,
        val toFace: Facing
    ) {
        fun wrap(facing: Facing, coord: Pair<Int, Int>): Pair<Facing, Pair<Int, Int>>? {
            val idx = from.indexOf(coord)
            if (idx < 0 && facing == fromFace) {
                return null
            }
            return Pair(toFace, to[idx])
        }
        fun inverse() : WrapInstr {
            return WrapInstr(to, from, toFace.opposite(), fromFace.opposite())
        }
    }

    class Face(val wall: Set<Pair<Int, Int>>, val floor: Set<Pair<Int, Int>>) {
        val world = wall.union(floor)
        private val topLeft = Pair(world.minOf { it.first }, world.minOf { it.second })
        private val bottomLeft = Pair(world.minOf { it.first }, world.maxOf { it.second })
        private val topRight = Pair(world.maxOf { it.first }, world.minOf { it.second })
        private val bottomRight = Pair(world.maxOf { it.first }, world.maxOf { it.second })

        val topEdge = world.filter { it.second == topLeft.second }.sortedBy { it.first }
        val leftEdge = world.filter { it.first == topLeft.first }.sortedBy { it.second }
        val bottomEdge = world.filter { it.second == bottomLeft.second }.sortedBy { it.first }
        val rightEdge = world.filter { it.first == topRight.first }.sortedBy { it.second }

        fun inArea(guidingPoint: Pair<Int, Int>, dimension: Int): Face {
            val xBound = guidingPoint.first until (guidingPoint.first + dimension)
            val yBound = guidingPoint.second until (guidingPoint.second + dimension)
            val newWall = wall.filter { it.first in xBound && it.second in yBound }.toSet()
            val newFloor = floor.filter { it.first in xBound && it.second in yBound }.toSet()
            return Face(newWall, newFloor)
        }
    }

    enum class Facing(val off: Pair<Int, Int>) {
        RIGHT(Pair(1, 0)),
        DOWN(Pair(0, 1)),
        LEFT(Pair(-1, 0)),
        UP(Pair(0, -1));

        fun opposite() : Facing{
            return this.turn("L").turn("L")
        }

        fun turn(direction: String): Facing {
            val faces = values()
            val currIdx = faces.indexOf(this)
            val change = when (direction) {
                "L" -> -1
                "R" -> 1
                else -> throw IllegalArgumentException("Can only turn L or R!")
            }
            val newIdx = (currIdx + change).mod(faces.size)
            return faces[newIdx]
        }
    }
}
