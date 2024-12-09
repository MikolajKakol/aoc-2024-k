package day09

import Day
import DayTest
import kotlin.test.Test

object DaySolved : Day {

    private const val DOT = "."

    override suspend fun part1(input: List<String>) = input[0]
        .let {
            buildList {
                var space = false
                var id = 0
                it.forEach {
                    if (space) {
                        repeat(it.digitToInt()) {
                            add(DOT)
                        }
                    } else {
                        repeat(it.digitToInt()) {
                            add(id.toString())
                        }
                        id++
                    }
                    space = !space
                }
            }.toMutableList()
        }
        .let { blocks ->
            blocks
                .asReversed()
                .onEachIndexed { index, s ->
                    fun MutableList<String>.swap(from: Int, to: Int) {
                        set(to, get(from))
                        set(from, DOT)
                    }

                    if (s != DOT) {
                        val to = blocks.indexOf(DOT)
                        val from = blocks.size - 1 - index
                        blocks.swap(from, to)
                    }
                }
                .asReversed()
        }
        .let {
            it
                .filterNot { it == DOT }
                .map(String::toLong)
                .mapIndexed { index, l -> index.toLong() * l }
                .sum()
        }

    override suspend fun part2(input: List<String>) = input[0]
        .let { data ->
            var space = false
            var id = 0
            var position = 0L
            data.map {
                val size = it.digitToInt()
                (if (space) {
                    Space(size, position)
                } else {
                    Block(id++, size, position)
                }).also {
                    position += size
                    space = !space
                }
            }
        }
        .let { memorySpaces ->
            val blocks = memorySpaces.filterIsInstance<Block>()
            val spaces = memorySpaces.filterIsInstance<Space>().toMutableList()
            fun sortSpaces() {
                spaces.sortBy(Space::position)
            }
            blocks.reversed().forEach { block ->
                val space = spaces.find { it.size >= block.size && it.position < block.position }
                if (space != null) {
                    spaces.add(Space(block.size, block.position))
                    block.position = space.position
                    spaces.remove(space)
                    if (space.size > block.size) {
                        spaces.add(Space(space.size - block.size, block.position + block.size))
                    }

                    sortSpaces()
                }
            }
            blocks.sumOf(Block::calcValue)
        }


    sealed interface MemorySpace {
        val size: Int
        val position: Long

        fun calcValue() = 0L
    }

    data class Block(val id: Int, override val size: Int, override var position: Long) : MemorySpace {
        override fun calcValue(): Long {
            return (position..<position + size).sumOf { it * id.toLong() }
        }
    }

    data class Space(override val size: Int, override val position: Long) : MemorySpace
}

class DaySolvedTest : DayTest(DaySolved) {
    @Test
    fun testAll() {
        testAll(1928L, 6384282079460L, 2858L, 6408966547049L)
    }
}