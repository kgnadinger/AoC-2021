fun main() {
    fun part1(input: List<String>): Long {
        return findNumberofFish(input, 80)
    }

    fun part2(input: List<String>): Long {
        return findNumberofFish(input, 256)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
//    println(part1(testInput))
    val input = readInput("Day06")
//    println(part1(input))
    println(part2(input))
}

fun findNumberofFish(input: List<String>, days: Int): Long {
    var fishes: MutableList<Int> =  input.first().split(',').map { it.toInt() }.toMutableList()

    var sum: Long = 0
    var computedFish: HashMap<Int, Long> = hashMapOf()
    for (fish in fishes) {
        println("Fish: $fish, computed: $computedFish")
        val computed = computedFish[fish]
        if (computed != null) {
            sum += computed
        } else {
            val found = findNumberOfFishRec(1, days - fish)
            computedFish[fish] = found
            sum += found
        }
    }

    return sum
}

fun findNumberOfFishRec(initial: Int, days: Int): Long {
    if (days <= 0) { return initial.toLong() }

    return findNumberOfFishRec(initial, days - 7) + findNumberOfFishRec(1, days - 9)
}
