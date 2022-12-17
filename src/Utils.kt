import Direction.*
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.math.absoluteValue

/**
 * Reads lines from the given input txt file.
 */
fun readLines(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Reads the input as a single String.
 */
fun readText(name: String) = File("src", "$name.txt")
    .readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

enum class Direction {
    North,
    NorthEast,
    East,
    SouthEast,
    South,
    SouthWest,
    West,
    NorthWest,
    Unknown;

    companion object {
        fun from(string: String): Direction {
            return when (string) {
                "R" -> East
                "U" -> North
                "L" -> West
                "D" -> South
                else -> Unknown
            }
        }
    }
}

//TODO make this typed ...(Char) -> T): Array<Array<T>>
fun inputTo2DArray(input: String, f: (Char) -> Int): Array<Array<Int>> {
    return input.split("\n").map {
        it.map { e -> f(e) }.toTypedArray()
    }.toTypedArray()
}

data class Point(val x: Int, val y: Int) {
    fun manhattanDinstanceTo(otherPoint: Point): Int {
        return (this.x - otherPoint.x).absoluteValue + (this.y - otherPoint.y).absoluteValue
    }

    fun stepIn(direction: Direction): Point {
        return pathInDirection(this, direction, 1)[0]
    }
}

fun pathInDirection(from: Point, direction: Direction, steps: Int): List<Point> {
    //TODO solve resursively
    var f = from
    return (steps downTo 1).map {
        when (direction) {
            North -> {
                f = Point(f.x, f.y + 1)
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
                f = Point(f.x, f.y - 1)
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

            Unknown -> {
                f
            }
        }
    }
}



fun direction(from: Point, to: Point): Direction {
    if (to.x == from.x) {
        return if (to.y == from.y) {
            Unknown
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

fun <T> List<T>.tail(): List<T> {
    return if (this.isEmpty()) emptyList()
    else this.takeLast(this.size - 1)
}