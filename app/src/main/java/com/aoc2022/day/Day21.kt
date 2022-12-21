package com.aoc2022.day

class Day21 : GenericDay() {

    override fun solve1(input: List<String>): Long {
        val toSolve = input.associate { Pair(it.split(": ")[0], it.split(": ")[1]) }.toMutableMap()
        return solve(toSolve, "root", -1)!!
    }

    override fun solve2(input: List<String>): Long {
        val separator = ": "
        val toSolve =
            input.associate { Pair(it.split(": ")[0], it.split(separator)[1]) }.toMutableMap()
        toSolve.remove("humn")
        toSolve["root"] = toSolve["root"]!!.replace("+", "=")

        return solve(toSolve, "root", -1)!!
    }

    private fun solveNormally(equations: Map<String, String>, wanted: String): Long? {
        val toKnow = equations[wanted] ?: return null // short-circuit if it's just an assignment
        if (toKnow.toLongOrNull() != null) {
            return toKnow.toLong()
        } else if (toKnow.contains("=")) {
            return null // don't even try to solve pt2
        }

        // solve recursively
        val (op1Str, operationStr, op2Str) = equations[wanted]!!.split(" ")
        val operation: (Long, Long) -> Long = when (operationStr) {
            "-" -> Long::minus
            "+" -> Long::plus
            "*" -> Long::times
            "/" -> Long::div
            else -> {
                throw IllegalArgumentException("Cannot parse expression!")
            }
        }
        val op1 = solveNormally(equations, op1Str)
        val op2 = solveNormally(equations, op2Str)

        return if (op1 != null && op2 != null) {
            operation.invoke(op1, op2)
        } else {
            null
        }
    }

    private fun solve(equations: Map<String, String>, wanted: String, expect: Long): Long? {
        val toKnow = equations[wanted] // short-circuit if the number is already known
        val short = solveNormally(equations, wanted)
        if (short != null) {
            return short
        }
        if (toKnow == null) {
            // this is what we've been searching for..
            return expect
        }

        // now we got a equation, that cannot be resolved here...
        val (op1Str, operationStr, op2Str) = equations[wanted]!!.split(" ")

        val op1 = solveNormally(equations, op1Str)
        val op2 = solveNormally(equations, op2Str)

        if (op1 == null && op2 == null) {
            throw IllegalStateException("Cannot resolve for multiple unknown operands...")
        }

        // which operand is not resolvable?!
        return if (op1 == null) {
            op2!!
            val newExpectation = when (operationStr) {
                "=" -> op2
                "+" -> expect - op2 // a = x + c // a - c = x
                "-" -> expect + op2 // a = x - c // a + c = x
                "*" -> expect / op2 // a = x * c // a / c = x
                "/" -> expect * op2 // a = x / c // a * c = x
                else -> throw IllegalArgumentException("Cannot parse argument!")
            }
            solve(equations, op1Str, newExpectation)
        } else {
            val newExpectation = when (operationStr) {
                "=" -> op1
                "+" -> expect - op1 // a = x + c // a - b = x
                "-" -> op1 - expect // a = x - c // c - a  = x
                "*" -> expect / op1 // expect = op1 * x // expect / op1 = x
                "/" -> op1 / expect // expect = op1 / x // op1 / expect = x
                else -> throw IllegalArgumentException("Cannot parse argument!")
            }
            solve(equations, op2Str, newExpectation)
        }
    }
}
