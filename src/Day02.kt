fun main() {
    fun part1(input: List<String>): Int {
        return calculatePosition(input)
    }

    fun part2(input: List<String>): Int {
        return calculatePositionWithAim(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    println(part2(testInput))
    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

fun calculatePosition(input: List<String>): Int {
    var position: Pair<Int, Int> = Pair<Int, Int>(0, 0)

    for (direction in input) {
        val splitString = direction.split(" ")
        val dir = splitString[0]
        val amount = splitString[1].toInt()
        when (dir) {
            "forward" -> {
                position = Pair(position.first + amount, position.second)
            }
            "down" -> {
                position = Pair(position.first, position.second + amount)
            }
            "up" -> {
                position = Pair(position.first, position.second - amount)
            }
        }
    }

    return position.first * position.second
}

fun calculatePositionWithAim(input: List<String>): Int {
    var position: Triple<Int, Int, Int> = Triple(0, 0, 0)

    for (direction in input) {
        val splitString = direction.split(" ")
        val dir = splitString[0]
        val amount = splitString[1].toInt()
        when (dir) {
            "forward" -> {
                position = Triple(position.first + amount, amount * position.third + position.second, position.third)
            }
            "down" -> {
                position = Triple(position.first, position.second, position.third + amount)
            }
            "up" -> {
                position = Triple(position.first, position.second, position.third - amount)
            }
        }
    }

    return position.first * position.second
}

