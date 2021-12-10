import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    fun part1(input: List<String>): Int {
        return findIllegalChar(input)
    }

    fun part2(input: List<String>): Long {
        return completeTheLines(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    println(part2(testInput))
    val input = readInput("Day10")
//    println(part1(input))
    println(part2(input))
}

fun findIllegalChar(input: List<String>) : Int {
    var illegalChars: MutableList<String> = mutableListOf()
    var chunks: HashMap<String, String> = hashMapOf(
            "(" to ")",
            "[" to "]",
            "{" to "}",
            "<" to ">"
    )

    var openChunks = chunks.keys
    var closingChunks = chunks.values

    lineloop@ for (line in input) {
        var chars: MutableList<String> = mutableListOf()
        charloop@ for (char in line) {
            var last = chars.lastOrNull()
            if (last != null) {
                // open, open
                if (openChunks.contains(last) && (openChunks.contains(char.toString()))) {
                    chars.add(char.toString())
                }
                // open, close
                else {
                    if (chunks[last] == char.toString()) {
                        chars.removeLast()
                    } else {
                        illegalChars.add(char.toString())
                        continue@lineloop
                    }
                }
            } else {
                if (closingChunks.contains(char.toString())) {
                    illegalChars.add(char.toString())
                    continue@lineloop
                } else {
                    chars.add(char.toString())
                }
            }
        }
    }

    var sum = 0
    for (char in illegalChars) {
        when (char) {
            ")" -> sum += 3
            "]" -> sum += 57
            "}" -> sum += 1197
            ">" -> sum += 25137
        }
    }

    return sum
}

fun completeTheLines(input: List<String>): Long {
    var missingChars: MutableList<List<String>> = mutableListOf()
    var chunks: HashMap<String, String> = hashMapOf(
            "(" to ")",
            "[" to "]",
            "{" to "}",
            "<" to ">"
    )

    var openChunks = chunks.keys
    var closingChunks = chunks.values

    var listOfLines: MutableList<String?> = input.toMutableList()

    lineloop@ for ((index, line) in input.withIndex()) {
        var chars: MutableList<String> = mutableListOf()
        charloop@ for (char in line) {
            var last = chars.lastOrNull()
            if (last != null) {
                // open, open
                if (openChunks.contains(last) && (openChunks.contains(char.toString()))) {
                    chars.add(char.toString())
                }
                // open, close
                else {
                    if (chunks[last] == char.toString()) {
                        chars.removeLast()
                    } else {
                        listOfLines[index] = null
                        continue@lineloop
                    }
                }
            } else {
                if (closingChunks.contains(char.toString())) {
                    listOfLines[index] = null
                    continue@lineloop
                } else {
                    chars.add(char.toString())
                }
            }
        }
    }

    lineloop@ for (line in listOfLines) {
        if (line != null) {
            var cachedList: MutableList<String> = mutableListOf()
            for (char in line) {
                if (cachedList.isEmpty()) {
                    cachedList.add(char.toString())
                } else {
                    val last = cachedList.last()
                    if (chunks[last] == char.toString()) {
                        cachedList.removeLast()
                    } else {
                        cachedList.add(char.toString())
                    }
                }
            }
            missingChars.add(cachedList.map { chunks[it] ?: ""})
        }
    }

    val answers = missingChars.map { it.reversed().joinToString("")}

    var scores: MutableList<Long> = mutableListOf()

    for (answer in answers) {
        var sum: Long = 0
        for (char in answer) {
            when (char.toString()) {
                ")" -> sum = sum * 5 + 1
                "]" -> sum = sum * 5 + 2
                "}" -> sum = sum * 5 + 3
                ">" -> sum = sum * 5 + 4
            }
        }
        scores.add(sum)
    }

    println(scores.sorted())

    return scores.sorted()[(scores.count()/2)]
}

