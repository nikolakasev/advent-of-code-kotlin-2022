
fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val firstHalf = it.chunked(it.length/2)[0].toCharArray().toSet()
            val secondHalf = it.chunked(it.length/2)[1].toCharArray().toSet()

            val asciiCode = (firstHalf intersect secondHalf).first().code.toByte()
            if (asciiCode >= 97) asciiCode - 96 else asciiCode - 38
        }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).sumOf {
            val first = it[0].toCharArray().toSet()
            val second = it[1].toCharArray().toSet()
            val third = it[2].toCharArray().toSet()

            val asciiCode = (first intersect second intersect third).first().code.toByte()
            if (asciiCode >= 97) asciiCode - 96 else asciiCode - 38
        }
    }

    val testInput = readLines("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readLines("Day03")
    
    println(part1(input))
    println(part2(input))
}