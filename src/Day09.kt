import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    fun part1(input: List<String>): Int {
        return findRelativeMins(input)
    }

    fun part2(input: List<String>): Int {
        return findBasins(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
//    println(part2(testInput))
    val input = readInput("Day09")
//    println(part1(input))
    println(part2(input))
}

fun findRelativeMins(input: List<String>): Int {
    var matrix: HashMap<Pair<Int,Int>, Int> = hashMapOf()

    for ((lineIndex, line) in input.withIndex()) {
        for ((index, char) in line.split("").filter { it.isNotEmpty() }.withIndex()) {
            matrix[Pair(lineIndex, index)] = char.toInt()
        }
    }

    var risks: MutableList<Int> = mutableListOf()

    rowLoop@ for (i in 0 until matrix.keys.count()) {
        columnLoop@ for (j in 0 until input.first().count()) {
            val current = matrix[Pair(i,j)]
            if (current != null) {
                var founderLower = false
                val up = matrix[Pair(i+1, j)]
                if (up != null && up <= current) { founderLower = true }
                var down = matrix[Pair(i-1,j)]
                if (down != null && down <= current) { founderLower = true }
                var left = matrix[Pair(i, j-1)]
                if (left != null && left <= current) { founderLower = true }
                var right = matrix[Pair(i, j+1)]
                if (right != null && right <= current) { founderLower = true }

                if (!founderLower) {
                    risks.add(current + 1)
                }

            } else {
                continue@columnLoop
            }
        }
    }

    return risks.reduce { acc, next -> acc + next }
}

fun findBasins(input: List<String>): Int {
    var cachedMatrix: HashMap<Pair<Int,Int>, Int?> = hashMapOf()
    var matrix: HashMap<Pair<Int,Int>, Int?> = hashMapOf()

    for ((lineIndex, line) in input.withIndex()) {
        for ((index, char) in line.split("").filter { it.isNotEmpty() }.withIndex()) {
            matrix[Pair(lineIndex, index)] = char.toInt()
        }
    }

    cachedMatrix = HashMap(matrix)

    var basins: MutableList<List<Pair<Int, Int>>> = mutableListOf()

    rowLoop@ for (i in 0 until matrix.keys.count()) {
        columnLoop@ for (j in 0 until matrix.keys.count()) {
            val starting = Pair(i,j)
            if ((matrix[starting] != null) && (matrix[starting] != 9)) {
                val pairs = findBasin(matrix, Pair(i, j))
                if (pairs.isNotEmpty()) {
                    basins.add(pairs)
                    for (pair in pairs) {
                        matrix[pair] = null
                    }
                }
            }
        }
    }

    return basins
        .map { it.count() }
        .sortedDescending()
        .take(3)
        .reduce { acc, next -> acc * next }
}

fun findBasin(matrix: HashMap<Pair<Int, Int>, Int?>, starting: Pair<Int, Int>): List<Pair<Int, Int>> {
    var basin: MutableList<Pair<Int, Int>> = mutableListOf()
    val current = matrix[starting]
    val i = starting.first
    val j = starting.second
    if (current != null) {
        basin.add(starting)
        matrix[starting] = null
        val up = matrix[Pair(i+1, j)]
        if (up != null && up != 9) {
            basin += findBasin(matrix, Pair(i + 1, j))
        }
        var down = matrix[Pair(i-1,j)]
        if (down != null && down != 9) {
            basin += findBasin(matrix, Pair(i-1,j))
        }
        var left = matrix[Pair(i, j-1)]
        if (left != null && left != 9) {
            basin += findBasin(matrix, Pair(i, j-1))
        }
        var right = matrix[Pair(i, j+1)]
        if (right != null && right != 9) {
            basin += findBasin(matrix, Pair(i, j+1))
        }
    }

    return basin.toList()
}