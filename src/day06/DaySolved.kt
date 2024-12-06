@file:Suppress("ControlFlowWithEmptyBody")

package day06

import Day
import DayTest
import util.Direction
import util.Matrix2D
import util.Point2D
import kotlin.test.Test

object DaySolved : Day {

    override suspend fun part1(input: List<String>) = GuardMap.create(input)
        .solve1()


    override suspend fun part2(input: List<String>) = GuardMap.create(input)
        .solve2()

    class GuardMap(private val maze: Matrix2D) {

        private val guardInitialPosition: Point2D = maze.find('^')

        init {
            maze.set(guardInitialPosition, '.')
        }

        fun solve1(): Any {
            val visitedPositions = mutableSetOf(guardInitialPosition)
            while (march(maze, guardInitialPosition, Direction.NORTH, visitedPositions)) {
            }

            return visitedPositions.size
        }

        suspend fun solve2(): Any {
            val visitedPositions = mutableSetOf(guardInitialPosition)
            while (march(maze, guardInitialPosition, Direction.NORTH, visitedPositions)) {
            }

            val possibleNewObstacles = visitedPositions.toList()
                .flatMap { point -> Direction.entries.map(point::move) }
                .toSet()

            return possibleNewObstacles
                .map {
                    maze.clone()
                        .apply {
                            if (getOrNull(it) != null) set(it, '#')
                        }
                }
                .count { march2(it, guardInitialPosition, Direction.NORTH, mutableSetOf()) }
        }

        private tailrec fun march(
            matrix2D: Matrix2D,
            guardPosition: Point2D,
            direction: Direction,
            visitedPositions: MutableSet<Point2D>,
        ): Boolean {
            val nextPosition = guardPosition.move(direction)
            val mapTile = matrix2D.getOrNull(nextPosition) ?: return false


            when (mapTile) {
                '.' -> {
                    visitedPositions.add(nextPosition)
                    return march(matrix2D, nextPosition, direction, visitedPositions)
                }

                '#' -> {
                    return march(matrix2D, guardPosition, direction.nextClockWise, visitedPositions)
                }
            }
            return true
        }

        private tailrec fun march2(
            matrix2D: Matrix2D,
            guardPosition: Point2D,
            direction: Direction,
            visitedPositions: MutableSet<Point2D>,
            turningPoints: MutableSet<Pair<Point2D, Direction>> = mutableSetOf(),
        ): Boolean {
            val nextPosition = guardPosition.move(direction)
            val mapTile = matrix2D.getOrNull(nextPosition) ?: return false


            when (mapTile) {
                '.' -> {
                    visitedPositions.add(nextPosition)
                    return march2(matrix2D, nextPosition, direction, visitedPositions, turningPoints)
                }

                '#' -> {
                    if (turningPoints.contains(guardPosition to direction))
                        return true
                    turningPoints.add(guardPosition to direction)
                    return march2(matrix2D, guardPosition, direction.nextClockWise, visitedPositions, turningPoints)
                }
            }
            return false
        }

        companion object {
            fun create(input: List<String>): GuardMap {
                return GuardMap(Matrix2D.create(input))
            }
        }
    }
}

class DaySolvedTest : DayTest(DaySolved) {
    @Test
    fun testAll() {
        testAll(41, 4722, 6, 1602)
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