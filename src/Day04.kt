
fun main() {
    fun part1(input: String): Int {
        return "\\d+".toRegex().findAll(input).chunked(4).count {
            (it[0].value.toInt() <= it[2].value.toInt() && it[1].value.toInt() >= it[3].value.toInt()) ||
            (it[0].value.toInt() >= it[2].value.toInt() && it[1].value.toInt() <= it[3].value.toInt())
        }
    }

    fun part2(input: String): Int {
        return "\\d+".toRegex().findAll(input).chunked(4).count {
            ((it[0].value.toInt()..it[1].value.toInt()).toSet() intersect (it[2].value.toInt()..it[3].value.toInt()).toSet()).isNotEmpty()
        }
    }

    val testInput = readText("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readText("Day04")

    println(part1(input))
    println(part2(input))
}