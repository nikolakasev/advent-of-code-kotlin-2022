import Direction.*
import kotlin.math.absoluteValue

fun main() {
    fun bothParts(input: List<String>, ropeLength: Int): Int {
        var head = Point(0,0)
        var tail = Point(0,0)

        var c = emptyList<Point>()
        input.forEach {
            val steps = it.split(" ")[1].toInt()
            val path = Direction.from(it.split(" ")[0])?.let { d ->
                goInDirection(head, d, steps)
            }

            if (!path.isNullOrEmpty()) { //discovered this technique to handle the null once which cleans up .map .toSet and .site
//                path?.map { h ->       //unnecessary safe call
                path.map { h ->
                    //tail acts
                    if ((h.x - tail.x).absoluteValue > (ropeLength - 1) || (h.y - tail.y).absoluteValue > (ropeLength - 1)) {
                        direction(tail, h)?.let { d ->
                            tail = follow(tail, d)
                            //TODO this logic could be tail-recursive instead adding the tail position to a list
                            c += tail
                        }
                    }
                }

                head = path.last()
            } else {
                //TODO this should be an error in the logic, how to handle that?
            }
        }

        return c.toSet().size + 1
    }

    val testInput = readLines("Day09_test")
    check(bothParts(testInput, 2) == 13)

    val testInput2 = readLines("Day09_test2")
//    check(bothParts(testInput2, 10) == 36)

    val input = readLines("Day09")
    println(bothParts(input, 2))
//    println(part2(input))
}

data class Point(var x: Int, var y: Int)
//    companion object {
//        fun follows(direction: Direction): Point {
//            return follow(super., direction)
//        }
//    }
//}

fun goInDirection(from: Point, direction: Direction, steps: Int): List<Point> {
    //TODO solve resursively
    var f = from
    return (steps downTo 1).map {
        when(direction) {
            North -> {
                f = Point(f.x , f.y + 1)
                f
            }
            NorthEast -> {
                f = Point(f.x + 1, f.y + 1)
                f
            }
            East -> {
                f = Point(f.x + 1, f.y)
                f
            }
            SouthEast -> {
                f = Point(f.x + 1, f.y - 1)
                f
            }
            South -> {
                f = Point(f.x , f.y - 1)
                f
            }
            SouthWest -> {
                f = Point(f.x - 1, f.y - 1)
                f
            }
            West -> {
                f = Point(f.x - 1, f.y)
                f
            }
            NorthWest -> {
                f = Point(f.x - 1, f.y + 1)
                f
            }
        }
    }
}

fun follow(from: Point, direction: Direction): Point {
    return goInDirection(from, direction, 1)[0]
}

fun direction(from: Point, to: Point): Direction? {
    if (to.x == from.x) {
        return if (to.y == from.y) {
            null
        } else if (to.y < from.y) {
            South
        } else {
            North
        }
    } else if (to.x < from.x) {
        return if (to.y == from.y) {
            West
        } else if (to.y < from.y) {
            SouthWest
        } else {
            NorthWest
        }
    } else {
        // to.x > from.x
        return if (to.y == from.y) {
            East
        } else if (to.y < from.y) {
            SouthEast
        } else {
            NorthEast
        }
    }
}