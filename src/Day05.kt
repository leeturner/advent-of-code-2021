
data class CoOrds(val x: Int, val y: Int) {
    override fun toString(): String = "($x,$y)"
}

data class LineSegment(val from: CoOrds, val to: CoOrds)

fun parseCoOrds(input: List<String>): List<LineSegment> {
    return input.map { line ->
        val (from, to) = line.split(" -> ")
        val (fromX, fromY) = from.split(",").map { it.toInt() }
        val (toX, toY) = to.split(",").map { it.toInt() }
        LineSegment(CoOrds(fromX, fromY), CoOrds(toX, toY))
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val lineSegments = parseCoOrds(input)
        println(lineSegments)
        val foo = lineSegments.filter { it.from.x == it.to.x || it.from.y == it.to.y }
        println(foo)



        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
//    check(part2(testInput) == 1924)

//    val input = readInput("Day05")
//    println("Final score of winning board = ${part1(input)}")
//    println("Final score of last winning board = ${part2(input)}")
}


