import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTime

abstract class DayTest(var day: Day, private val hasSameInputBetweenDays: Boolean = true) {

    var maxTestTime: Duration = 2.seconds

    fun testAll(
        testPart1: Any = Unit,
        realPart1: Any = Unit,
        testPart2: Any = Unit,
        realPart2: Any = Unit,
    ) {
        if (testPart1 != Unit) {
            println("\nrunning test 1")
            testPart1(testPart1)
        }
        if (realPart1 != Unit) {
            println("\nrunning real 1")
            realPart1(realPart1)
        }
        if (testPart2 != Unit) {
            println("\nrunning test 2")
            testPart2(testPart2)
        }
        if (realPart2 != Unit) {
            println("\nrunning real 2")
            realPart2(realPart2)
        }
    }
    protected fun testPart1(expected: Any) {
        solvePart1(expected, "test1")
    }

    protected fun testPart2(expected: Any) {
        solvePart2(expected, if (hasSameInputBetweenDays) "test1" else "test2")
    }

    protected fun realPart1(expected: Any) {
        solvePart1(expected, "real1")
    }

    protected fun realPart2(expected: Any) {
        solvePart2(expected, if (hasSameInputBetweenDays) "real1" else "real2")
    }


    protected fun solvePart1(expected: Any, part: String) = testFun({ day.part1(read(part)) }, expected)

    protected fun solvePart2(expected: Any, part: String) = testFun({ day.part2(read(part)) }, expected)

    private fun testFun(block: suspend () -> Any?, expected: Any) {
        runBlocking {
            withTimeout(timeout = maxTestTime) {
                val time = measureTime {
                    val actual = block()
                    println("got: $actual")
                    check(expected == actual)
                }
                println("took: $time")
            }
        }
    }

    private fun read(part: String): List<String> {
        val qualifiedName = day::class.qualifiedName
        val folder = qualifiedName!!.split(".")[0]
        return readInput("$folder/$part")
    }
}