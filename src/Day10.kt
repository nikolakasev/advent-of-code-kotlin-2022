fun main() {
    fun part1(input: List<String>): Int {
        return acc(input, 0, 1, 1, listOf(20, 60, 100, 140, 180, 220))
    }

    fun part2(input: List<Int>): Int {
        return input.sortedDescending().take(3).sum()
    }

    val testInput = readLines("Day10_test2")
    val input = readLines("Day10")

    check(part1(testInput) == 13140)
//    check(part2(testInput) == 45000)
//
    println(part1(input))
//    println(part2(input))
}

fun acc(input: List<String>, pointer: Int, currentCycle: Int, register: Int, cycles: List<Int>): Int {
    return if (cycles.isEmpty())
        0
    else {
        if (input[pointer] == "noop") {
            if (currentCycle == cycles[0]) {
                currentCycle * register + acc(input, pointer.inc(), currentCycle.inc(), register, cycles.takeLast(cycles.size - 1))
            } else {
                acc(input, pointer.inc(), currentCycle.inc(), register, cycles)
            }

        } else {
            val number = input[pointer].split(" ")[1].toInt()

            if (currentCycle == cycles[0]) {
                currentCycle * register +
                    acc(input, pointer.inc(), currentCycle + 2, register + number, cycles.takeLast(cycles.size - 1))
            } else if (currentCycle + 1 == cycles[0]) {
                (currentCycle + 1) * register +
                    acc(input, pointer.inc(), currentCycle + 2, register + number, cycles.takeLast(cycles.size - 1))
            } else {
                acc(input, pointer.inc(), currentCycle + 2, register + number, cycles)
            }
        }
    }
}