

fun main() {
    val regex =
        """
        (Monkey (\d):
          Starting items: ([\d,\s]*)
          Operation: new = old (\+|\*) (\w+)
          Test: divisible by (\d+)
            If true: throw to monkey (\d)
            If false: throw to monkey (\d))
        """.trimIndent().toRegex()

    val input = readText("Day11")

    regex.findAll(input).forEach {
        println(it)
    }
}