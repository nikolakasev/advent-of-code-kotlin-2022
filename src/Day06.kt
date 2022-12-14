fun main() {
    fun bothParts(input: String, markerLength: Int): Int {
        var c = 0
        for (i in input.windowed(markerLength)) {
            if (i.toCharArray().toSet().size < markerLength) c += 1
            else break
        }

        return c + markerLength
    }

    check(bothParts("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 4) == 7)
    check(bothParts("bvwbjplbgvbhsrlpgdmjqwftvncz", 4) == 5)
    check(bothParts("nppdvjthqldpwncqszvftbrmjlhg", 4) == 6)
    check(bothParts("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 4) == 10)
    check(bothParts("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 4) == 11)
    check(bothParts("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 14) == 19)

    val input = readText("Day06")

    println(bothParts(input, 4))
    println(bothParts(input, 14))
}