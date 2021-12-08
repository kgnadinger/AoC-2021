import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    fun part1(input: List<String>): Int {
        return findEasyNumbers(input)
    }

    fun part2(input: List<String>): Int {
        return findHardNumbers(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
//    println(part2(testInput))
    val input = readInput("Day08")
//    println(part1(input))
    println(part2(input))
}

fun findEasyNumbers(input: List<String>): Int {
    var sum = 0
    for (line in input) {
        var split = line.split(" | ")
        var signalInput = split.first().split(" ")
        var signalOutput = split.last().split(" ")
        var answerCounts = listOf<Int>(2, 3, 4, 7)
        sum += signalOutput.filter { answerCounts.contains(it.count()) }.count()
    }

    return sum
}

fun findHardNumbers(input: List<String>): Int {
    var sum = 0
    for (line in input) {
        sum += findSum(line)
    }

    return sum
}

fun findSum(input: String): Int {

    var zero = ""
    var one = ""
    var two = ""
    var three = ""
    var four = ""
    var five = ""
    var six = ""
    var seven = ""
    var eight = ""
    var nine = ""

    var signals = input.split(" | ").first().split(" ")
    for (sInput in signals) {
        when (sInput.count()) {
            2 -> one = sInput
            3 -> seven = sInput
            4 -> four = sInput
            7 -> eight = sInput
        }
    }

    // find 3
    three = findThree(seven, signals)

    // find 9
    nine = findNine(three, signals)

    var zeroSeg = findDifference(seven, four).first()
    var oneSeg = findDifference(nine, three).first()
    var fourSeg = findDifference(eight, nine).first()
    var sixSeg = findSixSeg(nine, four, zeroSeg)
    var threeSeg = findThreeSeg(three, seven, sixSeg)

    six = findSix(listOf(zeroSeg, oneSeg, threeSeg, fourSeg, sixSeg), signals)

    var fiveSeg = findDifference(six, listOf<String>(zeroSeg, oneSeg, threeSeg, fourSeg, sixSeg).joinToString("")).first()
    var twoSeg = findDifference(eight, listOf<String>(zeroSeg, oneSeg, threeSeg, fourSeg, fiveSeg, sixSeg).joinToString("")).first()


    zero = listOf(zeroSeg, oneSeg, twoSeg, fourSeg, fiveSeg, sixSeg).joinToString("")
    two = listOf(zeroSeg, twoSeg, threeSeg, fourSeg, sixSeg).joinToString("")
    five = listOf(zeroSeg, oneSeg, threeSeg, fiveSeg, sixSeg).joinToString("")

    val hashMap: HashMap<String, String> = hashMapOf()
    hashMap[zero] = "0"
    hashMap[one] = "1"
    hashMap[two] = "2"
    hashMap[three] = "3"
    hashMap[four] = "4"
    hashMap[five] = "5"
    hashMap[six] = "6"
    hashMap[seven] = "7"
    hashMap[eight] = "8"
    hashMap[nine] = "9"

    val outPut = input.split(" | ").last().split(" ")
    var numberOutput: String = ""
    numberloop@ for (num in outPut) {
        keyLoop@ for (key in hashMap.keys) {
            if (containsExactly(num, key)) {
                numberOutput += hashMap[key]
            }
        }
    }

    return numberOutput.toInt()
}

fun containsExactly(first: String, second: String): Boolean {
    val firstList = first.split("")
    val secondList = second.split("")
    return firstList.containsAll(secondList) && secondList.containsAll(firstList)
}

fun findDifference(first: String, second:String): List<String> {
    var difference = mutableListOf<String>()
    for (char in first) {
        if (!second.contains(char)) { difference.add(char.toString()) }
    }

    return difference.toList()
}

fun findThree(seven: String, possibilities: List<String>): String {
    for (poss in possibilities) {
        if (poss.count() == 5) {
            val diff = findDifference(poss, seven)
            if (findDifference(poss, seven).count() == 2) {
                return poss
            }
        }
    }
    return ""
}

fun findNine(three: String, possibilities: List<String>): String {
    for (poss in possibilities) {
        if (poss.count() == 6) {
            val diff = findDifference(poss, three)
            if (findDifference(poss, three).count() == 1) {
                return poss
            }
        }
    }

    return ""
}

fun findSixSeg(nine: String, four: String, zeroSeg: String): String {
    val combined = combineUniqueLists(four, zeroSeg)
    return findDifference(nine, combined).first()
}

fun findThreeSeg(three: String, seven: String, sixSeg: String): String {
    val diff = findDifference(three, seven)
    val withoutSixSeg = diff.filter { it != sixSeg }
    return withoutSixSeg.first()
}



fun findSix(segments: List<String>, possibilities: List<String>): String {
    for (poss in possibilities) {
        if (poss.count() == 6) {
            val segs = segments.joinToString("")
            val diff = findDifference(poss, segs)
            if (diff.count() == 1) { return poss }
        }
    }

    return ""
}

fun combineUniqueLists(first: String, second: String): String {
    var list = first.split("").filter { it.isNotEmpty() }.toMutableList()
    for (char in second.split("").filter { it.isNotEmpty() }) {
        if (!list.contains(char)) { list.add(char) }
    }
    return list.joinToString("")
}