import java.math.BigInteger

fun main() {
    fun bothParts(input: String, rounds: Int, worried: Boolean): Long {
        val regex =
            """
            (Monkey (\d):
              Starting items: ([\d,\s]*)
              Operation: new = old (\+|\*) (\w+)
              Test: divisible by (\d+)
                If true: throw to monkey (\d)
                If false: throw to monkey (\d))
            """.trimIndent().toRegex()

        val monkeys = regex.findAll(input).mapIndexed { index, matchResult ->
            val items = matchResult.groups[3]?.value?.split(", ")?.map { it.toBigInteger() }?.toMutableList()!!
            val monkey = Monkey(
                index,
                items,
                { a: BigInteger -> interacts(a, matchResult.groups[5]?.value!!, matchResult.groups[4]?.value!!) },
                matchResult.groups[6]?.value?.toInt()!!,
                matchResult.groups[7]?.value?.toInt()!!,
                matchResult.groups[8]?.value?.toInt()!!,
                0
            )
            monkey
        }.toList()

        val modulo = monkeys
            .map { it.divideBy }
            .reduce { acc, i -> acc * i }

        for (round in 1..rounds) {
            monkeys.forEach { m ->
                m.items.forEach { item ->
                    val number = m.op(item)
                    m.interactions += 1

                    throws(worry(worried, number, modulo), m.divideBy, m.monkeyOne, m.monkeyTwo, monkeys)
                }
                m.items = mutableListOf()
            }
        }

        val topTwo = monkeys.map { m ->
            m.interactions
        }.sortedDescending().take(2)

        return topTwo[0] * topTwo[1].toLong()
    }

    fun part1(input: String, rounds: Int, worried: Boolean): Long {
        return bothParts(input, rounds, worried)
    }

    fun part2(input: String): Long {
        return bothParts(input, 10000, false)
    }

    val testInput = readText("Day11_test")
    check(part1(testInput, 20, true) == 10605.toLong())
    check(part1(testInput, 10000, false) == 2713310158)

    val input = readText("Day11")
    println(part1(input, 20, true))
    println(part2(input))
}

data class Monkey(val id: Int, var items: MutableList<BigInteger>, val op: (BigInteger) -> BigInteger, val divideBy: Int, val monkeyOne: Int, val monkeyTwo: Int, var interactions: Int)

fun interacts(old: BigInteger, number: String, sign: String): BigInteger {
    val n = if (number == "old") old else number.toBigInteger()
    return when (sign) {
        "*" -> old * n
        "+" -> old + n
        else -> throw IllegalArgumentException("how to handle $sign?")
    }
}

fun throws(worryLevel: BigInteger, divideBy: Int, monkeyOne: Int, monkeyTwo: Int, monkeys: List<Monkey>) {
    if (worryLevel.mod(divideBy.toBigInteger()) == 0.toBigInteger())
        monkeys[monkeyOne].items.add(worryLevel)
    else
        monkeys[monkeyTwo].items.add(worryLevel)
}

fun worry(youShould: Boolean, number: BigInteger, modulo: Int): BigInteger {
    return if (youShould) number / 3.toBigInteger() else number.mod(modulo.toBigInteger())
}