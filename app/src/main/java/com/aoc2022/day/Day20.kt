package com.aoc2022.day

class Day20 : GenericDay() {

    override fun solve1(input: List<String>): Long {
        val edges = Edge.parse(input)
        edges.mix()

        return edges.groveCoords()
    }

    override fun solve2(input: List<String>): Long {
        val off = 811589153L
        val edges = Edge.parse(input, off)

        repeat(10){ edges.mix() }

        return edges.groveCoords()
    }

    private fun List<Edge>.groveCoords() : Long{
        val zero = this.find { it.num == 0L }!!

        val onethousand = zero.skip(1000)
        val twothousand = onethousand.skip(1000)
        val threethousand = twothousand.skip(1000)
        return onethousand.num + twothousand.num + threethousand.num
    }

    private fun List<Edge>.mix() {
        for(edge in this) {
            edge.move()
        }
    }

    class Edge(val num: Long, val totalEdges : Int) {
        private var _before: Edge? = null
        private var _after: Edge? = null

        private val after get() = _after!!

        fun move() {
            if(this.num == 0L) {
                return
            }
            val moves = this.num.mod (totalEdges-1)

            var csr = this._before
            this.remove()

            repeat(moves) { csr = csr!!.after }
            csr!!.insertAfter(this)
        }

        fun skip(skipAmt : Int) : Edge {
            var csr = this
            repeat(skipAmt) {
                csr = csr.after
            }
            return csr
        }

        fun remove() {
            val pastBefore = this._before
            val pastAfter = this._after

            pastBefore!!._after = pastAfter
            pastAfter!!._before = pastBefore

            this._before = null
            this._after = null
        }

        fun insertAfter(other: Edge) {
            this.after.insertBefore(other)
        }

        fun insertBefore(other: Edge) {
            val pastBefore = this._before!!

            pastBefore._after = other
            other._before = pastBefore

            other._after = this
            this._before = other
        }

        override fun toString(): String {
            val nums = mutableListOf(num)
            var csr = this.after

            while (csr != this) {
                nums.add(csr.num)
                csr = csr.after
            }
            return nums.toString()
        }

        companion object {
            fun parse(input: List<String>, multiplier : Long = 1): List<Edge> {
                val edges = input.map { Edge(it.toLong() * multiplier, input.size) }

                edges.last()._after = edges.first()
                edges.first()._before = edges.last()

                for ((prev, next) in edges.windowed(2, 1)) {
                    next._before = prev
                    prev._after = next
                }

                return edges
            }
        }
    }

}
