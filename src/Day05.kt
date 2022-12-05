fun main() {
    fun part1(crates: List<ArrayDeque<String>>, input: String): String {
        "(\\d+)".toRegex().findAll(input.split("\n\n")[1]).chunked(3).forEach {
            val amount = it[0].value.toInt()
            val from = it[1].value.toInt() - 1
            val to = it[2].value.toInt() - 1

            (1..amount).forEach { _ ->
                val crate = crates[from].removeFirst()
                crates[to].addFirst(crate)
            }
        }

        return crates.joinToString(separator = "") {
            it.first()
        }
    }

    fun part2(crates: List<ArrayDeque<String>>, input: String): String {
        "(\\d+)".toRegex().findAll(input.split("\n\n")[1]).chunked(3).forEach { it ->
            val amount = it[0].value.toInt()
            val from = it[1].value.toInt() - 1
            val to = it[2].value.toInt() - 1

            (1..amount)
                .map {
                    val crate = crates[from].removeFirst()
                    crate
                }
                .reversed()
                .forEach {
                    crates[to].addFirst(it)
                }

        }

        return crates.joinToString(separator = "") {
            it.first()
        }
    }

    val crates = listOf(ArrayDeque(listOf("N", "W", "B")),
        ArrayDeque(listOf("B", "M", "D", "T", "P", "S", "Z", "L")),
        ArrayDeque(listOf("R", "W", "Z", "H", "Q")),
        ArrayDeque(listOf("R", "Z", "J", "V", "D", "W")),
        ArrayDeque(listOf("B", "M", "H", "S")),
        ArrayDeque(listOf("B", "P", "V", "H", "J", "N", "G", "L")),
        ArrayDeque(listOf("S", "L", "D", "H", "F", "Z", "Q", "J")),
        ArrayDeque(listOf("B", "Q", "G", "J", "F", "S", "W")),
        ArrayDeque(listOf("J", "D", "C", "S", "M", "W", "Z")))

    val input = readText("Day05")

//    println(part1(crates, input))
    println(part2(crates, input))
}