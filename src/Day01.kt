fun main() {
    fun part1(input: List<Int>): Int {
        return input.maxOf { it }
    }

    fun part2(input: List<Int>): Int {
        return input.sortedDescending().take(3).sum()
    }

    val testInput = readText("Day01_test").split("\n\n").map{
        it.split("\n").sumOf(String::toInt)
    }

    val input = readText("Day01").split("\n\n").map{
        it.split("\n").sumOf(String::toInt)
    }

    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    println(part1(input))
    println(part2(input))
}
