import java.util.stream.Collectors
import java.util.stream.IntStream


fun main() {
    fun part1(input: List<String>): Int {
        return calculateGammaAndEpsilonRate(input)
    }

    fun part2(input: List<String>): Int {
        return calculateOxygenAndScrubberRate(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
//    println(part2(testInput))
    val input = readInput("Day03")
//    println(part1(input))
    println(part2(input))
}

fun calculateGammaAndEpsilonRate(input: List<String>): Int {
    val count = input.first().count()
    var matrix: MutableList<MutableList<Int>> = MutableList(count) { mutableListOf() }
    for (number in input) {
        for ((index, binary) in number.withIndex()) {
            val bin = binary.toString().toInt()
            matrix[index].add(bin)
        }
    }

    var gamma: String = ""
    var epsilon: String = ""

    for (list in matrix) {
        val grouped = list.groupingBy { it }.eachCount()
        val max = grouped.maxByOrNull { it.value }?.key
        val min = grouped.minByOrNull { it.value }?.key

        if (max != null) { gamma += max.toString() }
        if (min != null) { epsilon += min.toString() }
    }

    var gammaInt = gamma.toInt(radix = 2)
    var epsilonInt = epsilon.toInt(radix = 2)

    return gammaInt * epsilonInt
}

fun calculateOxygenAndScrubberRate(input: List<String>): Int {
    val gamma = findMostCommon(input, 0)
    println(gamma)
    val ep = findLeastCommon(input, 0)
    println(ep)
    return findMostCommon(input, 0).first().toInt(radix = 2) * findLeastCommon(input, 0).first().toInt(radix = 2)
}

fun findMostCommon(input: List<String>, index: Int): List<String> {
    var oneCount = 0
    var zeroCount = 0
    for (binary in input) {
        if (binary[index].toString() == "1") oneCount += 1 else zeroCount += 1
    }
    var returnList: MutableList<String> = mutableListOf()
    val max = if (oneCount >= zeroCount) "1" else "0"

    for (binary in input) {
        if (binary[index].toString() == max) { returnList.add(binary) }
    }

    if (returnList.count() == 1) { return returnList }

    return findMostCommon(returnList, index + 1)
}

fun findLeastCommon(input: List<String>, index: Int): List<String> {
    var oneCount = 0
    var zeroCount = 0
    for (binary in input) {
        if (binary[index].toString() == "1") oneCount += 1 else zeroCount += 1
    }
    var returnList: MutableList<String> = mutableListOf()
    val min = if (zeroCount <= oneCount) "0" else "1"

    for (binary in input) {
        if (binary[index].toString() == min) { returnList.add(binary) }
    }

    if (returnList.count() == 1) { return returnList }

    return findLeastCommon(returnList, index + 1)
}