import java.math.BigInteger
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

    fun part2(input: List<String>): BigInteger {
        val sensorsAndBeacons = inputToSensorsAndBeacons(input)

        val intersectionsWithY = sensorsAndBeacons.flatMap {
            val coverageRadius = it.first.manhattanDinstanceTo(it.second)
            intersectsAxisY(it.first, coverageRadius)
        }.toSet().sortedBy { point -> point.first }

        println(intersectionsWithY)

        // algo:
        // for each intersection, check if there is another one with the same slope only two positions away
        // for each of those two pairs of intersections
        // calculate the y and x
        // check with inRange returns false
        // calculate x * 4M + y and return

        // intersections with a difference of two are (241656, true), (241658, true) and (6650616, false), (6650618, false)
        val y = ((6650618 - 1) - (241658 - 1)) / 2
        val x = 6650618 - 1 - y

        return x.toBigInteger() * 4000000.toBigInteger() + y.toBigInteger()
    }

    val testInput = readLines("Day15_test")
    val input = readLines("Day15")

    check(part1(testInput, 10) == 26)

    println(part1(input, 2000000))
    println(part2(input))
}

fun coverageAtY(sensor: Point, coverageRadius: Int, y: Int): Set<Point> {
    return if (sensor.y - coverageRadius <= y && sensor.y + coverageRadius >= y) {
        val gap = (sensor.y - y).absoluteValue

        ((sensor.x - coverageRadius + gap)..(sensor.x + coverageRadius - gap)).map {
            Point(it, y)
        }.toSet()
    } else emptySet()
}

fun inRange(point: Point, sensor: Point, beacon: Point): Boolean {
    val coverageRadius = sensor.manhattanDinstanceTo(beacon)
    val distance = point.manhattanDinstanceTo(sensor)

    return distance <= coverageRadius
}

fun intersectsAxisY(sensor: Point, coverageRadius: Int): Set<Pair<Int, Boolean>> {
    val up = if (sensor.y - coverageRadius >= 0) {
        setOf(Pair(sensor.x - (sensor.y - coverageRadius), true), Pair(sensor.x + (sensor.y - coverageRadius), false))
    } else {
        setOf(Pair(sensor.x - (coverageRadius - sensor.y), true), Pair(sensor.x + (coverageRadius - sensor.y), false))
    }

    return setOf(Pair(sensor.x - (sensor.y + coverageRadius), true), Pair(sensor.x + (sensor.y + coverageRadius), false)).union(up)
}

fun inputToSensorsAndBeacons(input: List<String>): List<Pair<Point, Point>> {
    return input.map {
        val match = "Sensor at x=(-*\\d+), y=(-*\\d+): closest beacon is at x=(-*\\d+), y=(-*\\d+)".toRegex().find(it)
        Pair(
            Point(match!!.groups[1]?.value?.toInt()!!, match.groups[2]?.value?.toInt()!!),
            Point(match.groups[3]?.value?.toInt()!!, match.groups[4]?.value?.toInt()!!))
    }
}