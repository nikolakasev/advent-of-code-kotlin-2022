import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>, targetY: Int): Int {
        val sensorsAndBeacons = inputToSensorsAndBeacons(input)

        val inRange = sensorsAndBeacons.filter {
            val coverageRadius = it.first.manhattanDinstanceTo(it.second)
            val sensorY = it.first.y

            sensorY - coverageRadius <= targetY && sensorY + coverageRadius >= targetY
        }

        val pointsInRange = inRange.flatMap {
            val coverageRadius = it.first.manhattanDinstanceTo(it.second)
            coverageAtY(it.first, coverageRadius, targetY)
        }.toSet()

        val beacons = sensorsAndBeacons.map { it.second }.toSet()

        return pointsInRange.subtract(beacons).size
    }

    fun part2(input: List<String>, range: Int): Point {
        val sensorsAndBeacons = inputToSensorsAndBeacons(input)

        val coverage = sensorsAndBeacons.flatMap {
            val coverageRadius = it.first.manhattanDinstanceTo(it.second)
            totalCoverage(it.first, coverageRadius)
        }.toSet()

        val area = (0..range).flatMap { x ->
            (0..range).map { y ->
                Point(x, y)
            }
        }.toSet()

        return area.subtract(coverage).first()
    }

    val testInput = readLines("Day15_test")
    val input = readLines("Day15")

    check(part1(testInput, 10) == 26)
    check(part2(testInput, 20) == Point(14, 11))

    println(part1(input, 2000000))
//    println(part2(input, 4000000))

//    println(totalCoverage(Point(13, 2), 3))
}

fun coverageAtY(sensor: Point, coverageRadius: Int, y: Int): Set<Point> {
    return if (sensor.y - coverageRadius <= y && sensor.y + coverageRadius >= y) {
        val gap = (sensor.y - y).absoluteValue

        ((sensor.x - coverageRadius + gap)..(sensor.x + coverageRadius - gap)).map {
            Point(it, y)
        }.toSet()
    } else emptySet()
}

fun totalCoverage(sensor: Point, coverageRadius: Int): Set<Point> {
    return ((sensor.y - coverageRadius)..(sensor.y + coverageRadius)).flatMap {
        coverageAtY(sensor, coverageRadius, it)
    }.toSet()
}

fun inputToSensorsAndBeacons(input: List<String>): List<Pair<Point, Point>> {
    return input.map {
        val match = "Sensor at x=(-*\\d+), y=(-*\\d+): closest beacon is at x=(-*\\d+), y=(-*\\d+)".toRegex().find(it)
        Pair(Point(match!!.groups[1]?.value?.toInt()!!,
            match.groups[2]?.value?.toInt()!!),
            Point(match.groups[3]?.value?.toInt()!!,
                match.groups[4]?.value?.toInt()!!))
    }
}