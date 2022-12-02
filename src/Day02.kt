fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val him = it.split(" ")[0]
            val me = it.split(" ")[1]

            val gameResult = if (him == "A" && me == "X") {
                3 + 1
            } else if (him == "A" && me == "Y") {
                6 + 2
            } else if (him == "A" && me == "Z") {
                0 + 3
            } else if (him == "B" && me == "X") {
                0 + 1
            } else if (him == "B" && me == "Y") {
                3 + 2
            } else if (him == "B" && me == "Z") {
                6 + 3
            } else if (him == "C" && me == "X") {
                6 + 1
            } else if (him == "C" && me == "Y") {
                0 + 2
            }
            // (him == "C" && me == "Z")
            else {
                3 + 3
            }

            gameResult
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val him = it.split(" ")[0]
            val me = it.split(" ")[1]

            val gameResult = if (him == "A" && me == "X") {
                0 + 3
            } else if (him == "A" && me == "Y") {
                3 + 1
            } else if (him == "A" && me == "Z") {
                6 + 2
            } else if (him == "B" && me == "X") {
                0 + 1
            } else if (him == "B" && me == "Y") {
                3 + 2
            } else if (him == "B" && me == "Z") {
                6 + 3
            } else if (him == "C" && me == "X") {
                0 + 2
            } else if (him == "C" && me == "Y") {
                3 + 3
            }
            // (him == "C" && me == "Z")
            else {
                6 + 1
            }

            gameResult
        }
    }

    val testInput = readLines("Day02_test")
    val input = readLines("Day02")

    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    println(part1(input))
    println(part2(input))
}