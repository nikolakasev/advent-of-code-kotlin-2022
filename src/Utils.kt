import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

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
    NorthWest;

    companion object {
        fun from(string: String): Direction? {
            return when (string) {
                "R" -> East
                "U" -> North
                "L" -> West
                "D" -> South
                else -> null
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