package day05

import Day
import DayTest
import util.println
import kotlin.test.Test

object DaySolved : Day {

    override suspend fun part1(input: List<String>) = SoringMachine.parse(input)
        .run { solve1() }

    override suspend fun part2(input: List<String>) = SoringMachine.parse(input)
        .run { solve2() }


    data class SoringMachine(
        val rules: List<Pair<Int, Int>>,
        val pages: List<List<Int>>,
    ) {

        val sortedPages = pages.map { page ->
            page.toMutableList().apply {
                sortWith { p1, p2 ->
                    val rule = rules.find { (it.first == p1 && it.second == p2) || (it.first == p2 && it.second == p1) }
                    if (rule != null) {
                        if (rule.first == p1) {
                            -1
                        } else {
                            1
                        }
                    } else {
                        0
                    }
                }
            }
        }


        fun solve1() = pages
            .filter { page -> sortedPages.contains(page) }
            .sumOf { it[it.size / 2] }

        fun solve2() = sortedPages
            .filter { page -> !pages.contains(page) }
            .sumOf { it[it.size / 2] }

        companion object {
            fun parse(input: List<String>): SoringMachine {
                val rules = input.filter { it.contains("|") }
                    .map { it.split("|") }
                    .map { it[0].toInt() to it[1].toInt() }

                val pages = input.filter { it.contains(",") }
                    .map { it.split(",").map(String::toInt) }

                return SoringMachine(rules, pages)
            }
        }

    }
}

class DaySolvedTest : DayTest(DaySolved) {
    @Test
    fun testAll() {
        testAll(143, 4924, 123, 6085)
    }

    @Test
    fun testPart1() = testPart1(Unit)

    @Test
    fun realPart1() = realPart1(Unit)

    @Test
    fun testPart2() = testPart2(Unit)

    @Test
    fun realPart2() = realPart2(Unit)
}