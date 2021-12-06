typealias BoardData = List<List<Int>>

data class Cell(val value: Int, var marked: Boolean = false)
class Board private constructor(private val boardRows: List<MutableList<Cell>>) {

    fun mark(number: Int) {
        this.boardRows.forEach { row ->
            row.forEach { cell ->
                if (cell.value == number) cell.marked = true
            }
        }
    }

    fun hasWon(): Boolean {
        // check all rows
        val fullRow = boardRows.any { row -> row.all { it.marked } }
        // then all columns
        val fullColumn = this.checkColumns()

        return fullRow || fullColumn
    }

    private fun checkColumns(): Boolean {
        for (col in boardRows[0].indices) {
            var currentColumn = true
            for (row in boardRows.indices) {
                if (!boardRows[row][col].marked) {
                    currentColumn = false
                    continue
                }
            }
            if (currentColumn) return true
        }
        return false
    }

    fun getUnmarked() = this.boardRows
        .flatten()
        .filter { !it.marked }
        .map { it.value }

    companion object {
        fun of(boardData: BoardData): Board {
            return Board(boardData.map { row -> row.map { number -> Cell(number) }.toMutableList() })
        }
    }
}

fun generateBoards(input: List<String>): List<Board> {
    // First lets get all the lines groups together correctly
    // at the end of this we will have a list with an element for each board.  Each board will
    // be a list containing an element for each row and each row will be a list of ints
    // once we have done that we can then convert them all to Boards
    return input
        .asSequence()
        // drop the first line
        .drop(1)
        // remove all the blank lines
        .filter { it.isNotBlank() }
        // group them together in lists of 5
        .chunked(5)
        // split the strings and convert them into ints
        .map { board ->
            board.map { line ->
                line.split(" ")
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
            }
        }
        // then create the actual boards from the int data
        .map { Board.of(it) }
        .toList()
}

fun getDraws(drawData: String): List<Int> = drawData.split(",").map { it.toInt() }

fun main() {
    fun part1(input: List<String>): Int {
        // get the draws from the first row and convert them into a list of ints
        val draws = getDraws(input.first())

        // next we need all the boards.
        val boards = generateBoards(input)

        //next we loop through all the draws and mark the entries in the bingo boards
        var score = 0
        drawsLoop@ for (draw in draws) {
            boardLoop@ for (board in boards) {
                board.mark(draw)
                if (board.hasWon()) {
                    println("Current draw = $draw")
                    val sumOfUnmarked = board.getUnmarked().sum()
                    println("Sum of unmarked cells = $sumOfUnmarked")
                    score = draw * sumOfUnmarked
                    break@drawsLoop
                }
            }
        }

        return score
    }

    fun part2(input: List<String>): Int {
        // get the draws from the first row and convert them into a list of ints
        val draws = getDraws(input.first())

        // next we need all the boards.
        var boards = generateBoards(input)

        //next we loop through all the draws and mark the entries in the bingo boards
        var score = 0
        drawsLoop@ for (draw in draws) {
            boardLoop@ for (board in boards) {
                board.mark(draw)
                if (board.hasWon()) {
                    println("Current draw = $draw")
                    val sumOfUnmarked = board.getUnmarked().sum()
                    println("Sum of unmarked cells = $sumOfUnmarked")
                    score = draw * sumOfUnmarked
                    boards = boards - board
                }
            }
        }

        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println("Final score of winning board = ${part1(input)}")
    println("Final score of last winning board = ${part2(input)}")
}


