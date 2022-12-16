package com.aoc2022.day

class Day16 : GenericDay() {

    override fun solve1(input: List<String>): Int {
        val valves = Valve.parse(input)
        val start = valves[Valve.STARTING_POINT]!!

        val openable = valves.values.filter { it.rate != 0 }
        val paths = (listOf(start) + openable).map { it.dijkstra() }
        return pressureDijkstra(paths, 30).values.max()
    }

    override fun solve2(input: List<String>): Int {
        val valves = Valve.parse(input)
        val start = valves[Valve.STARTING_POINT]!!

        val openable = valves.values.filter { it.rate != 0 }.toSet()
        val paths = (listOf(start) + openable).map { it.dijkstra() }
        val pressureMap = pressureDijkstra(paths, 26)
        val withOpenValve: Map<Set<Valve>, List<ValveState>> = pressureMap.keys.groupBy { it.opened }

        val (maxSingleState, maxSinglePressure) = pressureMap.maxBy { it.value }

        var highestComb = Pair(maxSingleState, maxSingleState)
        var highestPressure = maxSinglePressure

        for (state in pressureMap.keys) {
            val currPressure = pressureMap[state]!!
            val minPressureNeeded = highestPressure - currPressure
            val inNeed = openable - state.opened // TODO, this is wrong. they maybe cannot touch all...
            val match = withOpenValve.keys.filter { inNeed.containsAll(it) }
                .flatMap { withOpenValve[it]!! }
                .filter { pressureMap[it]!! > minPressureNeeded }
                .maxByOrNull { pressureMap[it]!! }

            match?.let {
                highestComb = Pair(state, match)
                highestPressure = currPressure + pressureMap[match]!!
            }

        }

        return highestPressure
    }

    // https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
    fun pressureDijkstra(
        paths: List<ShortestPath>,
        maxMins: Int
    ): Map<ValveState, Int> { // more like brute force...
        val pathLookup = paths.associateBy { it.valve }
        val start = ValveState(paths.first().valve, setOf())
        val queue = mutableListOf(start)

        val pressure: MutableMap<ValveState, Int> = queue.associateWith { 0 }.toMutableMap()
        val minutes: MutableMap<ValveState, Int> = queue.associateWith { 0 }.toMutableMap()
        val prev: MutableMap<ValveState, ValveState?> = queue.associateWith { null }.toMutableMap()

        pressure[start] = 0

        while (queue.isNotEmpty()) {
            val u = queue.maxBy { pressure.getOrDefault(it, 0) }
            queue.remove(u)

            val nextValves = pathLookup[u.valve]!!.dist
                .filterKeys { u.valve != it && start.valve != it }
                .filterKeys { pathLookup.containsKey(it) }
                .filterKeys { !u.opened.contains(it) }
            for ((v, hops) in nextValves) {
                val pressurizedMins =
                    maxMins - minutes[u]!! - hops - 1 // without goin' there or opening it
                val alt = pressure.getOrDefault(u, 0) + (pressurizedMins * v.rate)

                val newState = ValveState(v, u.opened + listOf(v))
                val prevMins = minutes[u] ?: 0
                val newMins = prevMins + hops + 1

                if (newMins >= maxMins) {
                    continue
                }
                if (alt > pressure.getOrDefault(newState, 0)) {
                    // update, found a better way to the state
                    pressure[newState] = alt
                    minutes[newState] = newMins
                    prev[newState] = u

                    // continue that path, if it seems more viable...
                    queue.add(newState)
                }
            }
        }

        return pressure
    }

    class ValveState(val valve: Valve, val opened: Set<Valve>) {}

    class ShortestPath(val valve: Valve, val dist: Map<Valve, Int>, val prev: Map<Valve, Valve?>) {}

    class Valve(val id: String, val rate: Int) {
        private val _leadingTo = mutableListOf<Valve>()
        val leadingTo get() : List<Valve> = _leadingTo

        // https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
        fun dijkstra(
        ): ShortestPath {
            val start = this
            val queue = mutableListOf(start)
            val dist: MutableMap<Valve, Int> = queue.associateWith { Int.MAX_VALUE }.toMutableMap()
            val prev: MutableMap<Valve, Valve?> =
                queue.associateWith { null }.toMutableMap()

            dist[start] = 0

            while (queue.isNotEmpty()) {
                val u = queue.minBy { dist[it]!! }
                queue.remove(u)

                for (v in u.leadingTo) {
                    val alt = if (dist.containsKey(u)) {
                        dist[u]!! + 1
                    } else {
                        Int.MAX_VALUE
                    }
                    if (alt < dist.getOrDefault(v, Int.MAX_VALUE)) {
                        dist[v] = alt
                        prev[v] = u
                        queue.add(v)
                    }
                }
            }
            return ShortestPath(start, dist, prev)
        }

        override fun toString(): String {
            return "$id, rate=$rate => ${_leadingTo.map { it.id }}"
        }

        companion object {
            const val STARTING_POINT = "AA"

            fun parse(lines: List<String>): Map<String, Valve> {
                val values = lines.map { parseSingle(it) }
                val valveMap = values.map { Valve(it.first, it.second) }.associateBy { it.id }

                values.forEach { (id, _, leadingTo) ->
                    leadingTo.forEach { lead ->
                        valveMap[id]!!._leadingTo.add(valveMap[lead]!!)
                    }
                }
                return valveMap
            }

            private fun parseSingle(line: String): Triple<String, Int, List<String>> {
                //Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
                val (id, rateStr, leadingTo) = line.replace("Valve ", "")
                    .replace(" has flow rate=", ";")
                    .replace(" tunnels lead to valves ", "")
                    .replace(" tunnel leads to valve ", "")
                    .split(";")
                val rate = rateStr.toInt()
                val leadingToList = leadingTo.split(", ")
                return Triple(id, rate, leadingToList)
            }
        }
    }

}
