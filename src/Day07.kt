import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        return determineMinFuel(input)
    }

    fun part2(input: List<String>): Long {
        return determineMinFuelPart2(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    println(part2(testInput))
    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

fun determineMinFuel(input: List<String>): Int {
    val positions = input.first().split(',').map { it.toInt() }
    var max = positions.maxOrNull() ?: 0
    var min = positions.minOrNull() ?: 0


    var start = min
    var end = max
    var mid = 0
    var hasFound = false
    var fuel = 0
    while (!hasFound) {
        mid = start + (end - start) / 2
        val before = determineFuel(positions, mid - 1)
        val current = determineFuel(positions, mid)
        val after = determineFuel(positions, mid + 1)
        if ((current < before) && (current < after)) {
            hasFound = true
            fuel = current
        } else if (current > before) {
            end = mid - 1
        } else {
            start = mid + 1
        }
    }

    return fuel
}

fun determineMinFuelPart2(input: List<String>): Long {
    val positions = input.first().split(',').map { it.toInt() }
    var max = positions.maxOrNull() ?: 0
    var min = positions.minOrNull() ?: 0


    var start = min
    var end = max
    var mid = 0
    var hasFound = false
    var fuel: Long = 0
    while (!hasFound) {
        mid = start + (end - start) / 2
        val before = determineCrabEngineeringFuel(positions, mid - 1)
        val current = determineCrabEngineeringFuel(positions, mid)
        val after = determineCrabEngineeringFuel(positions, mid + 1)
        if ((current < before) && (current < after)) {
            hasFound = true
            fuel = current
        } else if (current > before) {
            end = mid - 1
        } else {
            start = mid + 1
        }
    }

    return fuel
}

fun determineCrabEngineeringFuel(crabs: List<Int>, position: Int): Long {
    var sum: Long = 0
    for (crab in crabs) {
        val movement = (crab - position).absoluteValue
        sum += (movement * (2 + (movement - 1))) / 2
    }
    return sum
}

fun determineFuel(crabs: List<Int>, position: Int): Int {
    var sum = 0
    for (crab in crabs) {
        sum += (crab - position).absoluteValue
    }

    return sum
}

