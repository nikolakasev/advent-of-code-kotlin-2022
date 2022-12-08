fun main() {
    fun inputTo2DArray(input: String): Array<Array<Int>> {

        return input.split("\n").map {
            it.chunked(1).map { tree ->
                tree.toInt()
            }.toTypedArray()
        }.toTypedArray()
    }

    fun part1(forest: Array<Array<Int>>): Int {
        var count = 0
        for (y in 1..forest.size - 2)
            for (x in 1..forest[0].size - 2) {
                if (checkIfVisible(x, y, forest)) count += 1
            }

        return count + 2 * forest.size + 2 * forest[0].size - 4
    }

    fun part2(forest: Array<Array<Int>>): Int {
        return (0 until forest.size).maxOf { y ->
            (0 until forest[0].size - 1).maxOf { x ->
                scenicScore(x, y, forest)
            }
        }
    }

    val testInput = inputTo2DArray(readText("Day08_test"))
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = inputTo2DArray(readText("Day08"))
    println(part1(input))
    println(part2(input))
}

fun checkIfVisible(treeX: Int, treeY: Int, forest: Array<Array<Int>>): Boolean {
    val top = (treeY - 1 downTo 0).all {
        forest[treeY][treeX] > forest[it][treeX]
    }

    val down = ((treeY + 1)..(forest.size - 1)).all {
        forest[treeY][treeX] > forest[it][treeX]
    }

    val left = (treeX - 1 downTo 0).all {
        forest[treeY][treeX] > forest[treeY][it]
    }

    val right = ((treeX + 1)..(forest[0].size - 1)).all {
        forest[treeY][treeX] > forest[treeY][it]
    }

    return top || left || down || right
}

fun scenicScore(treeX: Int, treeY: Int, forest: Array<Array<Int>>): Int {
    val up = (treeY - 1 downTo 0).indexOfFirst {
        forest[treeY][treeX] <= forest[it][treeX]
    }

    val upScore = if (up == -1)
        treeY
    else up + 1

    val down =
        ((treeY + 1)..(forest.size - 1)).indexOfFirst {
            forest[treeY][treeX] <= forest[it][treeX]
        }

    val downScore = if (down == -1)
        (forest.size - treeY - 1)
    else down + 1

    val left = (treeX - 1 downTo 0).indexOfFirst {
        forest[treeY][treeX] <= forest[treeY][it]
    }

    val leftScore = if (left == -1)
        treeX
    else left + 1

    val right =
        ((treeX + 1)..(forest[0].size - 1)).indexOfFirst {
            forest[treeY][treeX] <= forest[treeY][it]
        }

    val rightScore = if (right == -1)
        (forest[0].size - treeX - 1)
    else right + 1

    return upScore * leftScore * downScore * rightScore
}