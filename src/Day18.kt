fun main() {
    fun part1(input: List<String>): Int {
        val cubes = inputToCubes(input)
        return surfaceArea(cubes)
    }

    val testInput = readLines("Day18_test")
    val testInput2 = readLines("Day18_test2")
    val input = readLines("Day18")

    check(part1(testInput) == 10)
    check(part1(testInput2) == 64)
//    check(part2(testInput2) == 58)

    println(part1(input))
//    println(part2(input))
}

fun neighbours(x: Int, y: Int, z: Int): Set<Triple<Int, Int, Int>> {
    return setOf(
        Triple(x + 1, y, z), Triple(x - 1, y, z),
        Triple(x, y + 1, z), Triple(x, y - 1, z),
        Triple(x, y, z + 1), Triple(x, y, z - 1)
    )
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