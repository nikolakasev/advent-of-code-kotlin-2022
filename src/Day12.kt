fun main() {
    fun bothParts(input: Array<Array<Int>>, begin: Pair<Int, Int>): Int {
        val dist = mutableMapOf<Pair<Int, Int>, Int>()
        val q = ArrayDeque<Pair<Int, Int>>()

        for (y in 0..input.size - 1) {
            for (x in 0..input[y].size - 1) {
                val pair = Pair(x, y)
                dist[pair] = Int.MAX_VALUE
                q.add(pair)
            }
        }

        dist[begin] = 0
        val destination = Pair(88, 20)

        while (q.isNotEmpty()) {
            val v = q.minBy { pair -> dist[pair]!! }
            q.remove(v)
            getNeighbours(input, v.first, v.second).forEach {
                if (it in q) {
                    dist[it] = dist[v]!! + 1

                    if (it == destination) println("got one from " + begin + " at distance " + (dist[v]!! + 1))
                }
            }
        }

        return dist[destination]!!
    }

    fun part2(input: Array<Array<Int>>): Int {
        val aas = mutableListOf<Pair<Int, Int>>()

        for (y in 0..input.size - 1) {
            for (x in 0..input[y].size - 1) {
                if (input[y][x] == 97) aas.add(Pair(x, y))
            }
        }

        return aas.minOf {
            bothParts(input, it)
        }

//        return 0
    }

    fun part1(input: Array<Array<Int>>): Int {
        return bothParts(input, Pair(0, 20))
    }

//    val testInput = readText("Day12_test").replace('S', 'a').replace('E', 'z')
    val input = readText("Day12").replace('S', 'a').replace('E', 'z')

    println(part1(inputTo2DArray(input) { s -> s.code }))
    println(part2(inputTo2DArray(input) { s -> s.code }))
}

fun getNeighbours(input: Array<Array<Int>>, x: Int, y: Int): List<Pair<Int, Int>> {
    val value = if (input[y][x] == 83) 97 else if (input[y][x] == 69) 122 else input[y][x]
    val predicate = { a: Int, b: Int -> (b <= a || (b - a) == 1) }

    val top = try {
        input[y - 1][x]
    } catch (e: Exception) {
        null
    }

    val bottom = try {
        input[y + 1][x]
    } catch (e: Exception) {
        null
    }

    val left = try {
        input[y][x - 1]
    } catch (e: Exception) {
        null
    }

    val right  = try {
        input[y][x + 1]
    } catch (e: Exception) {
        null
    }

    return sequence {
        top?.let {
            if (predicate(value, it)) yield(Pair(x, y - 1))
        }

        bottom?.let {
            if (predicate(value, it)) yield(Pair(x, y + 1))
        }

        left?.let {
            if (predicate(value, it)) yield(Pair(x - 1, y))
        }

        right?.let {
            if (predicate(value, it)) yield(Pair(x + 1, y))
        }
    }.toList()
}