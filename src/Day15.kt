import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>, targetY: Int): Int {
        val sensorsAndBeacons = input.map {
            val match = "Sensor at x=(-*\\d+), y=(-*\\d+): closest beacon is at x=(-*\\d+), y=(-*\\d+)".toRegex().find(it)
            Pair(Point(match!!.groups[1]?.value?.toInt()!!,
                match.groups[2]?.value?.toInt()!!),
                Point(match.groups[3]?.value?.toInt()!!,
                    match.groups[4]?.value?.toInt()!!))
        }

        val inRange = sensorsAndBeacons.filter {
            val distance = it.first.manhattanDinstanceTo(it.second)
            val sensorY = it.first.y

            sensorY - distance <= targetY && sensorY + distance >= targetY
        }

        val pointsInRange = inRange.flatMap {
            val distance = it.first.manhattanDinstanceTo(it.second)
            coverageAtY(it.first, distance, targetY)
        }.toSet()

        val beacons = sensorsAndBeacons.map { it.second }.toSet()

        return pointsInRange.size - pointsInRange.intersect(beacons).size

    //        println(coverageAtY(Point(8, 7), 9, 0))

//        return 0
    }

    val testInput = readLines("Day15_test")
    val input = readLines("Day15")

    check(part1(testInput, 10) == 26)
//    check(part2(testInput) == 93)

    println(part1(input, 2000000))
//    println(part2(input))
}

fun coverageAtY(sensor: Point, coverageRadius: Int, y: Int): List<Point> {
    return if (sensor.y - coverageRadius <= y && sensor.y + coverageRadius >= y) {
//        (coverageRadius * 2 + 1) - (sensor.y - y).absoluteValue * 2
        val gap =(sensor.y - y).absoluteValue

        ((sensor.x - coverageRadius + gap)..(sensor.x + coverageRadius - gap)).map {
            Point(it, y)
        }

    } else emptyList()
}