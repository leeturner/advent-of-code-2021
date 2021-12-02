fun main() {
    data class Command(val keyword: String, val value: Int)

    fun part1(commands: List<Command>): Int {
        var depth = 0
        var horizontal = 0

        commands.forEach { (keyword, value) ->
            when (keyword) {
                "forward" -> horizontal += value
                "down" -> depth += value
                "up" -> depth -= value
                else -> throw IllegalArgumentException("Incorrect Command")
            }
        }

        println("depth: $depth & horizontal: $horizontal")
        // multiply those together
        return depth * horizontal
    }

    fun part2(commands: List<Command>): Int {
        var depth = 0
        var horizontal = 0
        var aim = 0

        commands.forEach { (keyword, value) ->
            when (keyword) {
                "forward" -> {
                    horizontal += value
                    depth += aim * value
                }
                "down" -> aim += value
                "up" -> aim -= value
                else -> throw IllegalArgumentException("Incorrect Command")
            }
        }

        println("depth: $depth & horizontal: $horizontal")
        // multiply those together
        return depth * horizontal
    }

    fun readCommands(filename: String): List<Command> {
        return readInput(filename).map {
            val (cmd, count) = it.split(" ")
            Command(cmd, count.toInt())
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readCommands("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readCommands("Day02")
    println("Position part 1: ${part1(input)}")
    println("Position part 2: ${part2(input)}")
}
