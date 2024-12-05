package dayX

import Day
import DayTest
import kotlin.test.Test

object DaySolved : Day {

    override suspend fun part1(input: List<String>) = input

    override suspend fun part2(input: List<String>) = input

}

class DaySolvedTest : DayTest(DaySolved) {
    @Test
    fun testAll() {
        testAll()
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