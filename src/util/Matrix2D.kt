package util

data class Matrix2D(
    val array: Array<CharArray> = emptyArray(),
) {

    fun clone(): Matrix2D {
        return Matrix2D(array.map { it.clone() }.toTypedArray())
    }

    val width: Int
        get() = array[0].size

    val height: Int
        get() = array.size

    val rows: List<CharArray>
        get() = array.toList()

    val columns: List<CharArray>
        get() = (0 until width)
            .map { x ->
                CharArray(height) { y ->
                    get(x, y)
                }
            }

    fun walk() = sequence {
        for (y in 0 until height) {
            for (x in 0 until width) {
                yield(Point2D(x, y))
            }
        }
    }

    fun traverse(action: (x: Int, y: Int, c: Char) -> Unit) {
        array.forEachIndexed { y, chars ->
            chars.forEachIndexed { x, char ->
                action(x, y, char)
            }
        }
    }

    fun get(point: Point2D): Char {
        return array[point.y][point.x]
    }

    fun get(x: Int, y: Int): Char {
        return array[y][x]
    }

    fun getInfinite(point: Point2D): Char {
        return getInfinite(point.x, point.y)
    }

    fun getInfinite(x: Int, y: Int): Char {
        var adjustedX = x % width
        var adjustedY = y % height
        if (adjustedX < 0) {
            adjustedX += width
        }
        if (adjustedY < 0) {
            adjustedY += height
        }
        return array[adjustedY][adjustedX]
    }

    fun getOrNull(point: Point2D): Char? {
        return getOrNull(point.x, point.y)
    }

    fun getOrNull(x: Int, y: Int): Char? {
        return if (x in 0 until width && y in 0 until height) {
            array[y][x]
        } else {
            null
        }
    }

    fun set(x: Int, y: Int, c: Char) {
        array[y][x] = c
    }

    fun set(point: Point2D, c: Char) {
        array[point.y][point.x] = c
    }

    fun find(value: Char): Point2D {
        return find { it == value }
    }

    fun find(matcher: (value: Char) -> Boolean): Point2D {
        for ((y, row) in array.withIndex()) {
            for ((x, item) in row.withIndex()) {
                if (matcher(item)) return Point2D(x, y)
            }
        }

        throw NoSuchElementException()
    }

    fun findAll(matcher: (value: Char) -> Boolean): List<Point2D> {
        val points = mutableListOf<Point2D>()
        for ((y, row) in array.withIndex()) {
            for ((x, item) in row.withIndex()) {
                if (matcher(item)) points.add(Point2D(x, y))
            }
        }

        return points
    }

    fun print() {
        array.forEach { row ->
            String(row).println()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix2D

        return array.contentDeepEquals(other.array)
    }

    override fun hashCode(): Int {
        return array.contentDeepHashCode()
    }

    companion object {
        fun create(input: List<String>): Matrix2D {
            val twoDArray = Array(input.size) { i -> input[i].toCharArray() }
            return Matrix2D(twoDArray)
        }

        fun create(width: Int, height: Int, init: (x: Int, y: Int) -> Char = { x, y -> '.' }): Matrix2D {
            val twoDArray = Array(height) { y ->
                CharArray(width) { x ->
                    init(x, y)
                }
            }
            return Matrix2D(twoDArray)
        }
    }
}
