package day03

import Day
import DayTest
import util.print
import util.println
import kotlin.test.Test

object DaySolved : Day {

    override suspend fun part1(input: List<String>) = input
        .joinToString("X")
        .let { program ->
            """mul\(([0-9]*),([0-9]*)\)"""
                .toRegex()
                .findAll(program)
                .map {
                    val (x, y) = it.groupValues.drop(1).map(String::toLong)
                    x * y
                }
                .sum()
        }

    override suspend fun part2(input: List<String>) = input
        .joinToString("X")
        .let { program ->
            var allow = true
            program.println()
            """mul\(([0-9]*),([0-9]*)\)|do\(\)|don't\(\)"""
                .toRegex()
                .findAll(program)
                .map {
                    val rule = it.groupValues[0]
                    when {
                        rule.startsWith("don") -> {
                            allow = false
                            0
                        }

                        rule.startsWith("do") -> {
                            allow = true
                            0
                        }

                        else -> {
                            val (x, y) = it.groupValues.drop(1).map(String::toLong)
                            if (allow) {
                                x * y
                            } else {
                                0
                            }
                        }
                    }
                }
                .sum()
        }
}

class DaySolvedTest : DayTest(DaySolved, hasSameInputBetweenDays = false) {

    @Test
    fun testAll() {
        testAll(161L, 178794710L, 48L, 76729637L)
    }
}