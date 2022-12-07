import ObjectType.Directory
import ObjectType.File
import java.lang.IllegalStateException

fun main() {
    fun inputToFileSystem(input: List<String>): List<Node> {
        var currentNode = Node("/", size = 0, Directory, parent = null)
        val nodes: MutableList<Node> = mutableListOf(currentNode)

        input.forEach {
            if (it.startsWith("$ cd")) {
                val dirName = it.split(" ")[2]

                currentNode = when (dirName) {
                    //go to root
                    "/" -> nodes[0]
                    //go to parent directory
                    ".." -> (currentNode.parent)?:throw IllegalStateException("can't find parent of ${currentNode.name}")
                    //go to dir !!!! multiple directories with the same name !!!!!
                    else -> (nodes.find { n -> n.name == dirName && n.parent == currentNode })?:throw IllegalStateException("can't find $dirName")
                }
            } else if (it.startsWith("$ ls")) {
                //do nothing
            } else {
                //contents
                val first = it.split(" ")[0]
                val second = it.split(" ")[1]

                if (first == "dir") nodes.add(Node(second, 0, Directory, currentNode))
                else nodes.add(Node(second, first.toInt(), File, currentNode))
            }
        }

        nodes.reversed().forEach {
            if (it.parent != null) {
                it.parent.size += it.size
            }
        }

        return nodes
    }

    fun part1(input: List<String>): Int {
        val nodes = inputToFileSystem(input)

        return nodes.sumOf {
            if (it.type == Directory && it.size <= 100000) it.size else 0
        }
    }

    fun part2(input: List<String>): Int {
        val nodes = inputToFileSystem(input)

        return nodes.filter {
            it.type == Directory && it.parent != null && it.size + 70000000 - nodes[0].size >= 30000000
        }.minOf {
            it.size
        }
    }

    val testInput = readLines("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readLines("Day07")
    println(part1(input))
    println(part2(input))
}

data class Node(val name: String, var size: Int, val type: ObjectType, val parent: Node?)
enum class ObjectType {
    Directory,
    File
}
