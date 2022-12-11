package com.aoc2022.day

class Day11 : GenericDay() {
    override fun solve1(input: List<String>): Long {
        val monkeys = Monkey.parse(input)

        for (round in 1..20) {
            for (monkey in monkeys) {
                monkey.throwAll(monkeys) { worry ->
                    worry / 3
                }
            }
            //println("Round $round")
            //monkeys.forEach { println(it.items) }
            //println()
        }
        val (top1, top2) =  monkeys.map { it.inspectCnt }.sortedDescending().take(2)
        return top1 * top2
    }

    override fun solve2(input: List<String>): Long {
        val monkeys = Monkey.parse(input)
        val lowestCommonMultiple = monkeys.map { it.divisor }.reduce { acc, x -> acc * x }

        for (round in 1..10_000) {
            for (monkey in monkeys) {
                monkey.throwAll(monkeys) { worry ->
                    worry % lowestCommonMultiple
                }
            }
        }
        val (top1, top2) =  monkeys.map { it.inspectCnt }.sortedDescending().take(2)
        return top1 * top2
    }

    class Monkey(
        initialItems: List<Long>,
        val operation: (Long) -> Long,
        val divisor: Long,
        val trueTrow: Int,
        val falseTrow: Int
    ) {
        val items = initialItems.toMutableList()
        val inspectCnt get() = _inspectCnt
        private val canThrow get() = items.isNotEmpty()

        private var _inspectCnt = 0L

        private fun inspect(): Long {
            _inspectCnt += 1
            val item = items.removeAt(0)
            return operation.invoke(item)
        }

        fun throwAll(monkeys : List<Monkey>, relief : (Long) -> Long) {
            while (this.canThrow) {
                this.throwTo(monkeys, relief)
            }
        }

        private fun throwTo(monkeys : List<Monkey>,relief : (Long) -> Long) {
            val item = relief.invoke(inspect())
            val monkeyIdx = if (item % divisor == 0L) {
                trueTrow
            } else {
                falseTrow
            }
            //println("throw to monkey $monkeyIdx, item: $relievedItem")
            monkeys[monkeyIdx].items.add(item)
        }

        companion object {
            fun parse(input: List<String>): List<Monkey> {
                val res = mutableListOf<Monkey>()
                /*
Monkey 0:
    Starting items: 79, 98
    Operation: new = old * 19
    Test: divisible by 23
        If true: throw to monkey 2
        If false: throw to monkey 3

                 */
                var items: List<Long> = listOf()
                var operation: (Long) -> Long = { old -> old }
                var divisor: Long = -1
                var trueTrow: Int = -1
                var falseTrow: Int = -1

                for (line in input.map { it.trim() }) {

                    if (line.startsWith("Monkey")) {
                        continue

                    } else if (line.startsWith("Starting items:")) {
                        val (_, itemsStr) = line.split(":")
                        items = itemsStr.trim().split(", ").map { it.toLong() }

                    } else if (line.startsWith("Operation: ")) {
                        val operands = line.split("=")[1].trim()
                        val (op1Str, operator, op2Str) = operands.split(" ")
                        operation = { old ->
                            val op1 = op1Str.toLongOrNull() ?: old
                            val op2 = op2Str.toLongOrNull() ?: old
                            when (operator) {
                                "*" -> op1 * op2
                                "+" -> op1 + op2
                                else -> throw IllegalArgumentException("Unable to parse operator $operator")
                            }
                        }
                    } else if (line.startsWith("Test: divisible by ")) {
                        divisor = line.split("by ")[1].trim().toLong()

                    } else if (line.startsWith("If true: throw to monkey ")) {
                        trueTrow = line.split("monkey ")[1].trim().toInt()

                    } else if (line.startsWith("If false: throw to monkey ")) {
                        falseTrow = line.split("monkey ")[1].trim().toInt()

                    } else if (line.isBlank()) {
                        res.add(Monkey(items, operation, divisor, trueTrow, falseTrow))
                    } else {
                        throw IllegalArgumentException("Unable to parse row: '$line'")
                    }
                }
                res.add(Monkey(items, operation, divisor, trueTrow, falseTrow))

                return res
            }
        }
    }
}

