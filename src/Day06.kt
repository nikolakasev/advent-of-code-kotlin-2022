fun main() {
    fun part1(input: String, markerLength: Int): Int {
        var c = 0
        for (i in input.windowed(markerLength)) {
            if (i.toCharArray().toSet().size < markerLength) c += 1
            else break
        }

        return c + markerLength
    }

    check(part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 4) == 7)
    check(part1("bvwbjplbgvbhsrlpgdmjqwftvncz", 4) == 5)
    check(part1("nppdvjthqldpwncqszvftbrmjlhg", 4) == 6)
    check(part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 4) == 10)
    check(part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 4) == 11)
    check(part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 14) == 19)

    val input = readText("Day06")

    println(part1(input, 4))
    println(part1(input, 14))
}