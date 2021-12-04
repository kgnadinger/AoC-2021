fun main() {
    fun part1(input: List<String>): Int {
        return findBingoWinner(input)
    }

    fun part2(input: List<String>): Int {
        return findBingoLoser(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
//    println(part2(testInput))
    val input = readInput("Day04")
//    println(part1(input))
    println(part2(input))
}

fun findBingoLoser(input: List<String>): Int {
    val chosen: MutableList<String> = input
        .first()
        .split(",")
        .toMutableList()
    var boards: MutableList<MutableList<MutableList<String>>> = mutableListOf()

    for ((index, board) in input.withIndex()) {
        if (index == 0) { continue }
        if (board.isEmpty()) {
            boards.add(mutableListOf())
        } else {
            val letters = board
                .split(" ")
                .filter { !it.isEmpty() }
            boards.last().add(letters.toMutableList())
        }
    }

    var transposedBoardPairs: MutableList<Pair<List<List<String>>, List<List<String>>>> = mutableListOf()

    for (board in boards) {
        val tBoard = matrixTranspose(board)
        val boardPair = Pair(board, tBoard)
        transposedBoardPairs.add(boardPair)
    }

    for ((index, _) in chosen.withIndex()) {
        val cachedList = transposedBoardPairs.toList()
        for (boardPair in cachedList) {
            val (first, second) = boardPair
            val numbers = chosen.slice(0..index)
            if (isBoardAWinner(first, numbers) || isBoardAWinner(second, numbers)) {
                if (transposedBoardPairs.count() == 1) {
                    return calculateScore(first, numbers)
                } else {
                    transposedBoardPairs.remove(boardPair)
                }
            }
        }
    }

    return 0
}

fun findBingoWinner(input: List<String>): Int {
    val chosen: MutableList<String> = input
        .first()
        .split(",")
        .toMutableList()
    var boards: MutableList<MutableList<MutableList<String>>> = mutableListOf()

    for ((index, board) in input.withIndex()) {
        if (index == 0) { continue }
        if (board.isEmpty()) {
            boards.add(mutableListOf())
        } else {
            val letters = board
                .split(" ")
                .filter { !it.isEmpty() }
            boards.last().add(letters.toMutableList())
        }
    }

    var transposedBoardPairs: MutableList<Pair<List<List<String>>, List<List<String>>>> = mutableListOf()

    for (board in boards) {
        val tBoard = matrixTranspose(board)
        val boardPair = Pair(board, tBoard)
        transposedBoardPairs.add(boardPair)
    }

    for ((index, _) in chosen.withIndex()) {
        for (boardPair in transposedBoardPairs) {
            val (first, second) = boardPair
            val numbers = chosen.slice(0..index)
            if (isBoardAWinner(first, numbers)) {
                return calculateScore(first, numbers)
            }

            if (isBoardAWinner(second, numbers)) {
                return calculateScore(second, numbers)
            }
        }
    }
    return 0
}

fun matrixTranspose(board: List<List<String>>): List<List<String>> {
    var tBoard: MutableList<MutableList<String>> = MutableList(board.count()) { mutableListOf() }

    for ((rowIndex, row) in board.withIndex()) {
        for ((columnIndex, num) in row.withIndex()) {
            tBoard[columnIndex].add(num)
        }
    }

    return tBoard.toList()
}



fun isBoardAWinner(board: List<List<String>>, letters: List<String>): Boolean {
    for (row in board) {
        if (letters.containsAll(row)) {
            return true
        }
    }

    return false
}

fun calculateScore(board: List<List<String>>, letters: List<String>): Int {
    var score = 0
    for (row in board) {
        for (num in row) {
            if (!letters.contains(num)) {
                score += num.toInt()
            }
        }
    }

    return score * letters.last().toInt()
}