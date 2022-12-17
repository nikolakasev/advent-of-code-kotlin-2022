import kotlin.math.absoluteValue

fun main() {
    fun bothParts(input: List<String>, ropeLength: Int): Int {
        // fill elements with ropeLength amount of Points set at coordinates 0, 0
        var elements = List(ropeLength) { Point(0, 0) }

        return input.flatMap {
            val steps = it.split(" ")[1].toInt()
            val head = elements.first()
            val path = pathInDirection(head, Direction.from(it.split(" ")[0]), steps + 1)

            path.map { p ->
                elements = simulateRope(p, elements, emptyList())
                elements.last()
            }
        }.toSet().size
    }

    val testInput = readLines("Day09_test")
    check(bothParts(testInput, 2) == 13)
    val testInput2 = readLines("Day09_test2")
    check(bothParts(testInput2, 10) == 36)

    val input = readLines("Day09")
    println(bothParts(input, 2))
    println(bothParts(input, 10))
}

fun simulateRope(head: Point, elements: List<Point>, acc: List<Point>): List<Point> {
    return if (elements.isEmpty()) {
        acc
    } else {
        val e = elements.first()
        if ((head.x - e.x).absoluteValue > 1 || (head.y - e.y).absoluteValue > 1) {
            val newPosition = e.stepIn(direction(e, head))
            simulateRope(newPosition, elements.tail(), acc.plus(newPosition))
        } else {
            acc.plus(elements)
        }
    }
}