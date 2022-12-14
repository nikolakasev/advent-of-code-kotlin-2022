fun main() {
    fun part1(input: List<String>): Int {
        val rocks = inputToRocks(input)

        //find the bottom
        val bottomY = rocks.maxBy { it.second }.second
        var intoTheAbyss = false
        val sand = rocks.toMutableSet()

        while(!intoTheAbyss) {
            var sandX = 500
            var sandY = 0

            //sand particle starts falling
            var cameAtRest = false
            while(!cameAtRest and !intoTheAbyss) {
                //hits support from straight down
                if (Pair(sandX, sandY + 1) in sand) {
                    //try to go down and left
                    if(Pair(sandX - 1, sandY + 1) in sand) {
                        //hits support from down and left
                        if(Pair(sandX + 1, sandY + 1) in sand) {
                            //hits support from down and right
                            cameAtRest = true
                            sand.add(Pair(sandX, sandY))
                        } else {
                            //go down and right
                            sandX += 1
                            sandY += 1
                        }
                    } else {
                        //go down and left
                        sandX -= 1
                        sandY += 1
                    }
                } else {
                    //keep on falling straight down
                    sandY += 1
                }

                if(sandY >= bottomY ) {
                    intoTheAbyss = true
                }
            }
        }

        return sand.size - rocks.size
    }

    fun part2(input: List<String>): Int {
        val rocks = inputToRocks(input)

        //find the bottom
        val bottomY = rocks.maxBy { it.second }.second + 2
        //union is very slow, so use only one set
        val sand = rocks.toMutableSet()

        //the source is not blocked by sand
        while(Pair(500, 0) !in sand) {
            var sandX = 500
            var sandY = 0

            //sand particle starts falling
            var cameAtRest = false
            while(!cameAtRest) {
                //hits support from straight down
                if (Pair(sandX, sandY + 1) in sand || (sandY + 1 == bottomY)) {
                    //try to go down and left
                    if(Pair(sandX - 1, sandY + 1) in sand || (sandY + 1 == bottomY)) {
                        //hits support from down and left
                        if(Pair(sandX + 1, sandY + 1) in sand || (sandY + 1 == bottomY)) {
                            //hits support from down and right
                            cameAtRest = true
                            sand.add(Pair(sandX, sandY))
                        } else {
                            //go down and right
                            sandX += 1
                            sandY += 1
                        }
                    } else {
                        //go down and left
                        sandX -= 1
                        sandY += 1
                    }
                } else {
                    //keep on falling straight down
                    sandY += 1
                }
            }
        }

        return sand.size - rocks.size
    }

    val testInput = readLines("Day14_test")
    val input = readLines("Day14")

    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    println(part1(input))
    println(part2(input))
}

fun inputToRocks(input: List<String>): Set<Pair<Int, Int>> {
    return input.flatMap {
        it.split(" -> ").windowed(2).flatMap { r ->
            pathBetween(r[0], r[1])
        }
    }.toSet()
}

fun pathBetween(from: String, to: String): List<Pair<Int, Int>> {
    return if (from == to) emptyList()
    else {
        val fromX = from.split(",")[0].toInt() //TODO define own extension similar to fun <T> List<T>.split() = Pair(take(1), drop(1))
        val fromY = from.split(",")[1].toInt()
        val toX = to.split(",")[0].toInt()
        val toY = to.split(",")[1].toInt()

//        // some smartness here from Fleet, change to fromX == toX and it will warn the value is always 0
//        val becomesAirPocket = if (fromY == toY) (fromX - toX).absoluteValue - 2 else 0

        return if (fromX == toX) {
            if (fromY < toY) {
                (fromY..toY).map {
                    Pair(fromX, it)
                }
            } else {
                (toY..fromY).map {
                    Pair(fromX, it)
                }
            }
        } else {
            if (fromX < toX) {
                (fromX..toX).map {
                    Pair(it, fromY)
                }
            } else {
                (toX..fromX).map {
                    Pair(it, fromY)
                }
            }
        }
    }
}