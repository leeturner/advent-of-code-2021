fun main() {
    fun part1(input: List<Int>): Int {
        val pairs = input.windowed(size = 2)
        return pairs.count { (a, b) -> a < b }
    }

    fun part2(input: List<Int>): Int {
        val triplesCount = input.windowed(size = 3).map { it.sum()  }
        val pairs = triplesCount.windowed(size = 2)
        return pairs.count { (a, b) -> a < b }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test").map { it.toInt() }
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01").map { it.toInt() }
    println("Number of increases in the input part 1: ${part1(input)}")
    println("Number of increases in the input part 2: ${part2(input)}")
}
