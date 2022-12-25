package com.aoc2022.day

internal operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first + other.first, this.second + other.second)
}

internal operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first - other.first, this.second - other.second)
}

internal operator fun Pair<Int, Int>.times(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first * other.first, this.second * other.second)
}

internal fun Pair<Int, Int>.mod(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first.mod(other.first), this.second.mod(other.second))
}