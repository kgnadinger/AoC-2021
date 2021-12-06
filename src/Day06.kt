fun main() {
    fun part1(input: List<String>): Int {
        return findOverlappingLines(input)
    }

    fun part2(input: List<String>): Int {
        return findOverlappingLinesWithDiagonal(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
//    println(part2(testInput))
    val input = readInput("Day05")
//    println(part1(input))
    println(part2(input))
}

data class Vector(val start: Pair<Int, Int>, val end: Pair<Int, Int>)

fun findOverlappingLines(input: List<String>) : Int {
    var vectors: MutableList<Vector> = mutableListOf()
    input.forEach {
        val pairs = it.split(" -> ")

        val firstPair = pairs.first().split(",")
        val fP = Pair(firstPair.first().toInt(), firstPair.last().toInt())

        val secPair = pairs.last().split(",")
        val sP = Pair(secPair.first().toInt(), secPair.last().toInt())

        if (fP.first == sP.first || fP.second == sP.second) {
            if (fP.first <= sP.first && fP.second <= sP.second) {
                vectors.add(Vector(fP, sP))
            } else {
                vectors.add(Vector(sP, fP))
            }
        }
    }

    var grid: HashMap<Pair<Int, Int>, Int> = hashMapOf()

    for (vector in vectors) {
        for (i in vector.start.first..vector.end.first) {
            for (j in vector.start.second..vector.end.second) {
//                println("i,j: $i,$j vector: $vector")
                val point = grid[Pair(i,j)]
                if (point == null) {
                    grid[Pair(i,j)] = 1
                } else {
                    grid[Pair(i,j)] = point + 1
                }
            }
        }
    }

    val sum = grid.filter {
        it.value >= 2
    }.keys.count()

    return sum
}

fun findOverlappingLinesWithDiagonal(input: List<String>) : Int {
    var vectorsNonDiaginal: MutableList<Vector> = mutableListOf()
    input.forEach {
        val pairs = it.split(" -> ")

        val firstPair = pairs.first().split(",")
        val fP = Pair(firstPair.first().toInt(), firstPair.last().toInt())

        val secPair = pairs.last().split(",")
        val sP = Pair(secPair.first().toInt(), secPair.last().toInt())

        if (fP.first == sP.first || fP.second == sP.second) {
            if (fP.first <= sP.first && fP.second <= sP.second) {
                vectorsNonDiaginal.add(Vector(fP, sP))
            } else {
                vectorsNonDiaginal.add(Vector(sP, fP))
            }
        }
    }

    var vectors: MutableList<Vector> = mutableListOf()
    input.forEach {
        val pairs = it.split(" -> ")

        val firstPair = pairs.first().split(",")
        val fP = Pair(firstPair.first().toInt(), firstPair.last().toInt())

        val secPair = pairs.last().split(",")
        val sP = Pair(secPair.first().toInt(), secPair.last().toInt())

        if (fP.first != sP.first && fP.second != sP.second) {
            vectors.add(Vector(fP, sP))
        }
    }

    var grid: HashMap<Pair<Int, Int>, Int> = hashMapOf()

    for (vector in vectorsNonDiaginal) {
        for (i in vector.start.first..vector.end.first) {
            for (j in vector.start.second..vector.end.second) {
                val point = grid[Pair(i,j)]
                if (point == null) {
                    grid[Pair(i,j)] = 1
                } else {
                    grid[Pair(i,j)] = point + 1
                }
            }
        }
    }

    for (vector in vectors) {
        val start = vector.start
        val end = vector.end
        var i = 0
        var iEnd = 0
        var j = 0
        var jEnd = 0

        var increaseSecond = false
        var increaseFirst = false

        i = vector.start.first
        j = vector.start.second
        iEnd = vector.end.first
        jEnd = vector.end.second

        increaseFirst = i < iEnd
        increaseSecond = j < jEnd

        do {
            println("i,j: $i,$j vector: $vector")
            val point = grid[Pair(i,j)]
            if (point == null) {
                grid[Pair(i,j)] = 1
            } else {
                grid[Pair(i,j)] = point + 1
            }
            if (increaseFirst) {
                i += 1
            } else {
                i -= 1
            }
            if (increaseSecond) {
                j += 1
            } else {
                j -= 1
            }
        } while (i != iEnd || j != jEnd)
        println("i,j: $i,$j vector: $vector")
        val point = grid[Pair(i,j)]
        if (point == null) {
            grid[Pair(i,j)] = 1
        } else {
            grid[Pair(i,j)] = point + 1
        }

    }
    val sum = grid.filter {
        it.value >= 2
    }.keys.count()

    return sum
}