import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        val cubes = inputToCubes(input)
        return surfaceArea(cubes)
    }

    fun part2(input: List<String>): Int {
        val cubes = inputToCubes(input)
        val minX = cubes.minByOrNull { it.first }
        val minY = cubes.minByOrNull { it.second }
        val minZ = cubes.minByOrNull { it.third }
        val min = min(min(minX!!.first, minY!!.second), minZ!!.third)

        val maxX = cubes.maxByOrNull { it.first }
        val maxY = cubes.maxByOrNull { it.second }
        val maxZ = cubes.maxByOrNull { it.third }
        val max = max(max(maxX!!.first, maxY!!.second), maxZ!!.third)

        val startingPoint = Triple(0, 0, 0)
        val visited = mutableSetOf<Triple<Int, Int, Int>>()
        var surfaceArea = 0

        val q = ArrayDeque<Triple<Int, Int, Int>>()
        q.add(startingPoint)
        while (q.isNotEmpty()) {
            val c = q.removeFirst()

            if (c !in visited) {
                visited.add(c)

                val neighbours = withinBounds(min - 1, max + 1, neighbours(c.first, c.second, c.third))
                    .subtract(visited)

                for (n in neighbours) {
                    if (n in cubes) {
                        surfaceArea += 1
                    } else q.add(n)
                }
            }
        }

        return surfaceArea
    }

    val testInput = readLines("Day18_test")
    val testInput2 = readLines("Day18_test2")
    val input = readLines("Day18")

    check(part1(testInput) == 10)
    check(part1(testInput2) == 64)
    check(part2(testInput2) == 58)
    check(part2(testInput2) == 58)

    println(part1(input))
    println(part2(input))
}

fun neighbours(x: Int, y: Int, z: Int): Set<Triple<Int, Int, Int>> {
    return setOf(
        Triple(x + 1, y, z), Triple(x - 1, y, z),
        Triple(x, y + 1, z), Triple(x, y - 1, z),
        Triple(x, y, z + 1), Triple(x, y, z - 1)
    )
}

fun withinBounds(min: Int, max: Int, cubes: Set<Triple<Int, Int, Int>>): Set<Triple<Int, Int, Int>> {
    return cubes.filter {
        it.first in min..max
            && it.second in min..max
            && it.third in min..max
    }.toSet()
}

fun inputToCubes(input: List<String>): Set<Triple<Int, Int, Int>> {
    return input.map {
        val coordinates = it.split(",")
        Triple(coordinates[0].toInt(), coordinates[1].toInt(), coordinates[2].toInt())
    }.toSet()
}

fun surfaceArea(cubes: Set<Triple<Int, Int, Int>>): Int {
    return cubes.sumOf { c ->
        val rest = cubes - c
        6 - neighbours(c.first, c.second, c.third).intersect(rest).size
    }
}