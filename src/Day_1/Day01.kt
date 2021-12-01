fun main() {
    val folder = "Day_1"
    fun part1(input: List<String>): Int {
        return countIncreases(input)
    }

    fun part2(input: List<String>): Int {
        return countTupleIncreases(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test", folder)
//    println(part2(testInput))
    val input = readInput("Day01", folder)
    println(part1(input))
    println(part2(input))
}

fun countIncreases(input: List<String>): Int {
    var count: Int = 0
    var previous: Int? = null
    for (num in input) {
        val numToInt = num.toInt()
        if (previous != null && numToInt > previous) {
            count += 1
        }
        previous = numToInt
    }
    return count
}

fun countTupleIncreases(input: List<String>): Int {
    var count: Int = 0
    var arr: MutableList<Int> = mutableListOf()
    // [[0, 1, 2], [1, 2, 3], [2, 3, 4], ... ]
    // count = 0 add to 0 index
    // count = 1 add to 0 index and 1 index
    // count = 2 add to 0 index, 1 index, 2 index
    // count = 3 add to 1 index, 2 index, 3 index
    for ((index, num) in input.withIndex()) {
        val numToInt = num.toInt()
        arr.add(index, numToInt)
        when (index) {
            0 -> continue
            1 ->  arr[0] += numToInt
            else -> {
                arr[index - 2] += numToInt
                arr[index - 1] += numToInt
            }
        }
        if (index > 2) {
            if (arr[index - 3] < arr[index - 2]) {
                count += 1
            }
        }
    }

    return count
}
