package day01

import Day
import DayTest
import kotlin.math.abs
import kotlin.test.Test

object DaySolved : Day {

    var index = 0
    override suspend fun part1(input: List<String>) = input
        .flatMap { text ->
            val (first, second) = text.split("   ")
                .map { it.toInt() }
            listOf(first, second)
        }
        .groupBy { index++ % 2 }
        .let {
            val first = it[0]!!.sortedDescending()
            val second = it[1]!!.sortedDescending()

            first.zip(second) { a, b ->
                abs(a - b)
            }
                .sum()
        }

    override suspend fun part2(input: List<String>) = input
        .flatMap { text ->
            val (first, second) = text.split("   ")
                .map { it.toInt() }
            listOf(first, second)
        }
        .groupBy { index++ % 2 }
        .let {
            val first = it[0]!!
            val second = it[1]!!

            first
                .map { firstItem->
                    second.count { it == firstItem } * firstItem
                }
                .sum()

        }
}

class DaySolvedTest : DayTest(DaySolved, hasSameInputBetweenDays = true) {

    @Test
    fun testPart1() = testPart1(11)

    @Test
    fun realPart1() = realPart1(936063)

    @Test
    fun testPart2() = testPart2(31)

    @Test
    fun realPart2() = realPart2(23150395)
}