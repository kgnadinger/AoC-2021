fun main() {
    fun part1(input: List<String>): Int {
        return determineNumberOfFlashes(input)
    }

    fun part2(input: List<String>): Int {
        return determineStep(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    println(part2(testInput))
    val input = readInput("Day11")
//    println(part1(input))
    println(part2(input))
}

fun determineNumberOfFlashes(input: List<String>): Int {
    var grid: HashMap<Pair<Int, Int>, Int> = hashMapOf()

    for ((i, line) in input.withIndex()) {
        for ((j, state) in line.withIndex()) {
            grid[Pair(i,j)] = state.toString().toInt()
        }
    }

    var sum = 0
    for (num in 0 .. 99) {
        val step = applyStep(grid)
        grid = step.first
        sum += step.second
    }

    println(convertGrid(grid))



    return sum
}

fun determineStep(input: List<String>): Int {
    var grid: HashMap<Pair<Int, Int>, Int> = hashMapOf()

    for ((i, line) in input.withIndex()) {
        for ((j, state) in line.withIndex()) {
            grid[Pair(i,j)] = state.toString().toInt()
        }
    }

    var step: Int = 0
    var hasFound = false
    while (!hasFound) {
        step += 1
        val appliedStep = applyStepForSync(grid)
        grid = appliedStep.first
        hasFound = appliedStep.second
    }

    println(convertGrid(grid))



    return step
}

fun convertGrid(grid: HashMap<Pair<Int, Int>, Int>): String {
    var gridString = ""

    for (key in grid.keys.sortedWith(compareBy({ it.first }, { it.second }))) {
        if (key.second == 0) {
            gridString += "\n${grid[key]}"
        } else {
            gridString += grid[key].toString()
        }
    }

    return gridString
}

fun applyStep(grid: HashMap<Pair<Int, Int>, Int>): Pair<HashMap<Pair<Int, Int>, Int>, Int> {
    for (key in grid.keys) {
        grid[key] = (grid[key] ?: 0) + 1
    }

    var flashedKeys: MutableList<Pair<Int, Int>> = mutableListOf()
    var hasFlashed = grid.keys.filter { (grid[it] ?: 0) > 9 }.isNotEmpty()
    var recentlyFlashedKeys: MutableList<Pair<Int, Int>> = mutableListOf()

    while (hasFlashed) {
        for (key in recentlyFlashedKeys) {
            val surroundingKeys = listOf<Pair<Int, Int>>(
                    Pair(key.first - 1, key.second),
                    Pair(key.first + 1, key.second),
                    Pair(key.first, key.second - 1),
                    Pair(key.first, key.second + 1),
                    Pair(key.first - 1, key.second - 1),
                    Pair(key.first - 1, key.second + 1),
                    Pair(key.first + 1, key.second + 1),
                    Pair(key.first + 1, key.second - 1)
            )
            for (adKey in surroundingKeys) {
                if (!flashedKeys.contains(adKey) && ((adKey.first >= 0) && (adKey.first <= 9) && (adKey.second >= 0) && (adKey.second <= 9))) {
                    grid[adKey] = (grid[adKey] ?: 0) + 1
                }
            }
            recentlyFlashedKeys = mutableListOf()
        }

        for (key in grid.keys) {
            if ((grid[key] ?: 0) > 9) {
                recentlyFlashedKeys.add(key)
                flashedKeys.add(key)
                grid[key] = 0
            }
        }
        hasFlashed = !recentlyFlashedKeys.isEmpty()
    }

    return Pair(grid, flashedKeys.count())
}

fun applyStepForSync(grid: HashMap<Pair<Int, Int>, Int>): Pair<HashMap<Pair<Int, Int>, Int>, Boolean> {
    for (key in grid.keys) {
        grid[key] = (grid[key] ?: 0) + 1
    }

    var flashedKeys: MutableList<Pair<Int, Int>> = mutableListOf()
    var hasFlashed = grid.keys.filter { (grid[it] ?: 0) > 9 }.isNotEmpty()
    var recentlyFlashedKeys: MutableList<Pair<Int, Int>> = mutableListOf()

    while (hasFlashed) {
        for (key in recentlyFlashedKeys) {
            val surroundingKeys = listOf<Pair<Int, Int>>(
                    Pair(key.first - 1, key.second),
                    Pair(key.first + 1, key.second),
                    Pair(key.first, key.second - 1),
                    Pair(key.first, key.second + 1),
                    Pair(key.first - 1, key.second - 1),
                    Pair(key.first - 1, key.second + 1),
                    Pair(key.first + 1, key.second + 1),
                    Pair(key.first + 1, key.second - 1)
            )
            for (adKey in surroundingKeys) {
                if (!flashedKeys.contains(adKey) && ((adKey.first >= 0) && (adKey.first <= 9) && (adKey.second >= 0) && (adKey.second <= 9))) {
                    grid[adKey] = (grid[adKey] ?: 0) + 1
                }
            }
            recentlyFlashedKeys = mutableListOf()
        }

        for (key in grid.keys) {
            if ((grid[key] ?: 0) > 9) {
                recentlyFlashedKeys.add(key)
                flashedKeys.add(key)
                grid[key] = 0
            }
        }
        hasFlashed = !recentlyFlashedKeys.isEmpty()
    }

    return Pair(grid, flashedKeys.count() == grid.keys.count())
}

