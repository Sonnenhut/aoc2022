package com.aoc2022.day

class Day19 : GenericDay() {

    override fun solve1(input: List<String>): Int {
        val blueprints = Blueprint.parse(input)
        val start = Blueprint.ProduceState(
            Resource.values().associateWith { 0 } + mapOf(Pair(Resource.ORE, 1)),
            Resource.values().associateWith { 0 }
            ,0
        )

        return blueprints.parallelStream()
            .map { blueprint ->
                println("-> Looking at blueprint ${blueprint.id}")
                val res = blueprint.bruteForce(start, 24)
                println("<- Blueprint #${blueprint.id}: $res geodes")
                blueprint.id * res
            }.reduce{ a,b -> a + b}.get()
    }

    override fun solve2(input: List<String>): Int {
        val blueprints = Blueprint.parse(input)
        val start = Blueprint.ProduceState(
            Resource.values().associateWith { 0 } + mapOf(Pair(Resource.ORE, 1)),
            Resource.values().associateWith { 0 }
            ,0
        )

        return blueprints.take(3).parallelStream()
            .map { blueprint ->
                println("-> Looking at blueprint ${blueprint.id}")
                val res = blueprint.bruteForce(start, 32)
                println("<- Blueprint #${blueprint.id}: $res geodes")
                res
            }.reduce{ a,b -> a * b}.get()
    }

    enum class Resource {
        ORE,
        CLAY,
        OBSIDIAN,
        GEODE
    }

    class Blueprint(val id: Int, val bench: Map<Resource, Map<Resource, Int>>) :
        HashMap<Resource, Map<Resource, Int>>(bench) {

        class ProduceState(val robots: Map<Resource, Int>, val inventory : Map<Resource, Int>, val minute: Int) {
            val geodes get() = inventory[Resource.GEODE]!!
        }

        private operator fun Map<Resource, Int>.minus(other: Map<Resource, Int>) : Map<Resource, Int> {
            val res = this.toMutableMap()
            for (key in other.keys) {
                res[key] = res[key]!! - other[key]!!
            }
            return res
        }

        private operator fun Map<Resource, Int>.plus(other: Map<Resource, Int>) : Map<Resource, Int> {
            val res = this.toMutableMap()
            for (key in other.keys) {
                res[key] = res[key]!! + other[key]!!
            }
            return res
        }

        fun ProduceState.futures() : List<ProduceState> {
            val res = mutableListOf<ProduceState>()
            val newMinute = this.minute + 1

            val whenBuild = mutableMapOf<Resource, ProduceState>()
            for (robot in bench.keys) {
                val newInv = this.inventory - bench[robot]!!
                if(newInv.values.none { it < 0} ) {
                    val newRobots = this.robots + mapOf(Pair(robot, 1))
                    whenBuild[robot] = ProduceState(newRobots, newInv, newMinute)
                }
            }
            // short-circuits...
            if(whenBuild[Resource.GEODE] != null) { // always build geode if possible
                res.add(whenBuild[Resource.GEODE]!!)
            } else {
                var idleAllowed = true
                for(newResourceRobot in whenBuild.keys) {
                    val maxOfResourceNeeded : Int = bench.values.maxOf { it[newResourceRobot] ?: 0 }
                    if(maxOfResourceNeeded >= this.robots[newResourceRobot]!!) {
                        // don't build more robots than the max of its resource we would need
                        res.add(whenBuild[newResourceRobot]!!)
                    }
                    //TODO this assumption is pretty high?  maxOfResourceNeeded < inventory is not enough though!
                    if((maxOfResourceNeeded*2) < inventory[newResourceRobot]!!) {
                        // if we have double inventory, we shouldn't just idle...
                        idleAllowed = false
                    }
                }
                if(idleAllowed) {
                    res.add(0, ProduceState(this.robots, this.inventory, newMinute))
                }
            }

            return res
        }

        fun ProduceState.collect() : Map<Resource, Int> {
            return this.robots
        }

        fun ProduceState.addInventory(addInv : Map<Resource, Int>) : ProduceState {
            return ProduceState(robots, inventory + addInv, this.minute)
        }

        fun bruteForce(start: ProduceState, maxMins : Int): Int {
            val queue = mutableListOf(start)
            var maxGeodeState = start

            while (queue.isNotEmpty()) {
                val u = queue.removeLast()

                // suppose we throw out geode bots each minute, can we beat the current max?
                if(u.geodesInPerfectFuture(maxMins) < maxGeodeState.geodes) {
                    continue
                }

                if(u.minute == maxMins) {
                    if(maxGeodeState.geodes < u.geodes) {
                        maxGeodeState = u
                    }
                    continue // don't continue for longer than 24 ticks...
                }

                val others = u.futures().map { it.addInventory(u.collect()) }
                queue.addAll(others)
            }
            return maxGeodeState.geodes
        }

        fun ProduceState.geodesInPerfectFuture(atMin: Int) : Int {
            var res = this.geodes
            var min = this.minute
            var bots = this.robots[Resource.GEODE]!!

            if(atMin == this.minute) {
                return this.geodes
            }

            do {
                min += 1
                res += bots
                bots +=1
            } while (min < atMin)

            return res
        }

        companion object {
            fun parse(input: List<String>) : List<Blueprint> {
                val parseNumAndType : (String) -> Map<Resource, Int> = {desc -> // parses "4 ore and 18 clay" or "3 ore"
                    val m = mutableMapOf<Resource, Int>()
                    desc.split(" and ").forEach { need ->
                        val (numStr, typeStr) = need.split(" ")
                        m[Resource.valueOf(typeStr.uppercase())] = numStr.toInt()
                    }
                    m
                }

                val res = mutableListOf<Blueprint>()

                //Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
                for (line in input) {
                    val print = mutableMapOf<Resource, Map<Resource, Int>>()
                    val id = line.replace("Blueprint ", "").split(":")[0].toInt()
                    line.substringAfter(":").split(".").take(4).forEach { origDesc ->
                        val (type, needs) = origDesc.replace(" Each ", "").split(" robot costs ")
                        print[Resource.valueOf(type.uppercase())] = parseNumAndType(needs)
                    }
                    res.add(Blueprint(id, print))
                }

                return res
            }
        }
    }
}
