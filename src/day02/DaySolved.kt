package day02

import Day
import DayTest
import kotlinx.coroutines.yield
import util.retrieveNumbers
import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.test.Test

object DaySolved : Day {

    override suspend fun part1(input: List<String>) = input
        .map { it.retrieveNumbers() }
        .map {
            if (isListSafe(it))
                1
            else
                0
        }
        .sum()

    override suspend fun part2(input: List<String>) = input
        .map { it.retrieveNumbers() }
        .map { list ->
            val safe = sequence {
                yield(list)
                list.onEachIndexed { index, _ ->

                    yield(list.toMutableList().apply { removeAt(index) })
                }
            }
                .any { isListSafe(it) }

            if (safe)
                1
            else
                0
        }
        .sum()

    private fun isListSafe(it: List<Int>): Boolean {
        val sign = (it[0] - it[1]).sign
        val safeCount = it.asSequence()
            .windowed(2, 1)
            .takeWhile {
                val diff = it[0] - it[1]
                diff.absoluteValue <= 3 && diff.sign == sign
            }
            .count()

        return safeCount + 1 == it.count()
    }

}

class DaySolvedTest : DayTest(DaySolved) {

    @Test
    fun testPart1() = testPart1(2)

    @Test
    fun realPart1() = realPart1(591)

    @Test
    fun testPart2() = testPart2(4)

    @Test
    fun realPart2() = realPart2(621)
}