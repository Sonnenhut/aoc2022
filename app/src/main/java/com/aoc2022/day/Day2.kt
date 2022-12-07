package com.aoc2022.day

class Day2 : GenericDay() {
    override fun solve1(input: List<String>): Int {
        var score = 0
        for (line in input) {
            val (opp, _, me) = line.toCharArray()
            val (oppHand, myHand) = Pair(Hand.valueOf(opp), Hand.valueOf(me))
            val res = myHand.play(oppHand)
            score += res.score + myHand.score
        }
        return score
    }

    override fun solve2(input: List<String>): Int {
        var score = 0
        for (line in input) {
            val (opp, _, expect) = line.toCharArray()
            val (oppHand, expectedResult) = Pair(Hand.valueOf(opp), GameResult.valueOf(expect))
            val myHand = expectedResult.findHand(oppHand)
            score += expectedResult.score + myHand.score
        }
        return score
    }
}

enum class GameResult(val score: Int) : FindHand {
    WIN(6) {
        override fun findHand(other: Hand): Hand {
            return when(other) {
                Hand.ROCK -> Hand.PAPER
                Hand.SCISSOR -> Hand.ROCK
                Hand.PAPER -> Hand.SCISSOR
            }
        }
    },
    TIE(3) {
        override fun findHand(other: Hand): Hand {
            return other
        }
    },
    LOSE(0) {
        override fun findHand(other: Hand): Hand {
            return when(other) {
                Hand.ROCK -> Hand.SCISSOR
                Hand.SCISSOR -> Hand.PAPER
                Hand.PAPER -> Hand.ROCK
            }
        }
    };

    companion object {
        fun valueOf(chr: Char): GameResult {
            return when (chr) {
                'X' -> LOSE
                'Y' -> TIE
                'Z' -> WIN
                else -> throw IllegalArgumentException("Unable to parse game result: $chr")
            }
        }
    }
}

interface FindHand {
    fun findHand(other: Hand) : Hand
}

enum class Hand(val score: Int) {
    ROCK(1), // TODO: store winning, tie, and losing to retrieve it instead of implementing it...
    PAPER(2),
    SCISSOR(3);

    fun play(opp: Hand): GameResult {
        var res = GameResult.LOSE
        if (this == opp) {
            res = GameResult.TIE
        } else if ((this == ROCK && opp == SCISSOR)
            || (this == SCISSOR && opp == PAPER)
            || (this == PAPER && opp == ROCK)
        ) {
            res = GameResult.WIN
        }
        return res
    }

    companion object {
        fun valueOf(chr: Char): Hand {
            return when (chr) {
                'A' -> Hand.ROCK
                'B' -> Hand.PAPER
                'C' -> Hand.SCISSOR
                // for pt1
                'X' -> Hand.ROCK
                'Y' -> Hand.PAPER
                'Z' -> Hand.SCISSOR
                else -> throw IllegalArgumentException("Unable to parse hand: $chr")
            }
        }
    }
}
