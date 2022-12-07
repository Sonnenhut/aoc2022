package com.aoc2022.day

abstract class GenericDay {
    abstract fun solve1(input : List<String>) : Any
    abstract fun solve2(input : List<String>) : Any
}

class DayNotImplemented : GenericDay() {
    override fun solve1(input: List<String>): Any {
        return "not yet implemented"
    }

    override fun solve2(input: List<String>): Any {
        return "not yet implemented"
    }

}