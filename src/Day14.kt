fun main() {
    fun part1(input: List<String>): Int {
        val rocks = input.flatMap {
            it.split(" -> ").windowed(2).flatMap { r ->
                pathBetween(r[0], r[1])
            }
        }

        //find the bottom
        val bottomY = rocks.maxBy { it.second }.second
        var intoTheAbyss = false
        val particles = mutableSetOf<Pair<Int, Int>>()

        while(!intoTheAbyss) {
            var sandX = 500
            var sandY = 0

            //sand particle starts falling
            var cameAtRest = false
            while(!cameAtRest and !intoTheAbyss) {
                //hits support from straight down
                if (Pair(sandX, sandY + 1) in rocks.union(particles)) {
                    //try to go down and left
                    if(Pair(sandX - 1, sandY + 1) in rocks.union(particles)) {
                        //hits support from down and left
                        if(Pair(sandX + 1, sandY + 1) in rocks.union(particles)) {
                            //hits support from down and right
                            cameAtRest = true
                            particles.add(Pair(sandX, sandY))
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

        return particles.size
    }

//    fun part2(input: List<String>) {
//
//        }
//    }

    val testInput = readLines("Day14_test")
    val input = readLines("Day14")

    check(part1(testInput) == 24)
    print(part1(input))
//    println(part1(input))
//    part2(testInput)
//
//    println(part1(input))
//    part2(input)
}

fun pathBetween(from: String, to: String): List<Pair<Int, Int>> {
    return if (from == to) emptyList()
    else {
        val fromX = from.split(",")[0].toInt() //TODO define own extension similar to fun <T> List<T>.split() = Pair(take(1), drop(1))
        val fromY = from.split(",")[1].toInt()
        val toX = to.split(",")[0].toInt()
        val toY = to.split(",")[1].toInt()

        if (fromX == toX) {
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