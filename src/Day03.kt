typealias BitCount = MutableList<Pair<Int, Int>>

fun List<String>.processBitCounts(): BitCount {
    // set up a list of  pairs to hold the counts of 1 or 0 for each bit
    // maybe should have created my own class given Pairs are immutable?
    // 0 are the first part of the pair, 1 are the second part of the pair
    val results = MutableList(this[0].length) { Pair(0, 0) }

    // loop through and count 1 or 0 for each bit in the binary number
    this.forEach { binary ->
        val bits = binary.split("").filter { it.isNotBlank() }
        bits.forEachIndexed { index, bit ->
            when (bit) {
                "0" -> results[index] = results[index].copy(first = results[index].first + 1)
                "1" -> results[index] = results[index].copy(second = results[index].second + 1)
            }
        }
    }
    return results
}

fun main() {

    fun part1(input: List<String>): Int {
        // set up a list of  pairs to hold the counts of 1 or 0 for each bit
        // maybe should have created my own class given Pairs are immutable?
        // 0 are the first part of the pair, 1 are the second part of the pair
        val results: BitCount = input.processBitCounts()

        // now we have the counts for all the 1s or 0s we can calculate the gamma rate and the epsilon rate
        val gammaRate = StringBuilder()
        val epsilonRate = StringBuilder()

        results.forEach {
            // calculate the gamma rate
            if (it.first > it.second) gammaRate.append("0") else gammaRate.append("1")
            // calculate the epsilon rate
            if (it.first > it.second) epsilonRate.append("1") else epsilonRate.append("0")
        }
        println(results)
        println("The Gamma Rate is: $gammaRate and the Epsilon Rate is: $epsilonRate")
        // convert binary to decimal and multiply
        return gammaRate.toString().toInt(2) * epsilonRate.toString().toInt(2)
    }

    fun generateRating(input: List<String>, equalsBit: Char, commonBit: Char, leastCommonBit: Char): String {
        var processedResults = input
        var rating = ""
        var index = 0
        println("Starting results: $processedResults")
        do {
            val results: BitCount = processedResults.processBitCounts()
            println("Results: $results")
            val (first, second) = results[index]
            if (first == second) {
                processedResults = processedResults.filter { it[index] == equalsBit }
                println("Results: $processedResults")
            } else {
                processedResults = if (first > second) {
                    processedResults.filter { it[index] == commonBit }
                } else {
                    processedResults.filter { it[index] == leastCommonBit }
                }
                println("Results: $processedResults")
            }
            if (processedResults.size == 1) {
                rating = processedResults[0]
            }
            index++
        } while (rating == "")

        return rating
    }

    fun part2(input: List<String>): Int {
        // let's find the oxygen generator rating
        val oxygenGeneratorRating = generateRating(input, '1', '0', '1')
        println("Oxygen Generator Rating: $oxygenGeneratorRating")

        // let's find the CO2 scrubber rating
        val co2scrubberRating = generateRating(input, '0', '1', '0')
        println("CO2 scrubber rating: $co2scrubberRating")

        // convert binary to decimal and multiply
        return oxygenGeneratorRating.toInt(2) * co2scrubberRating.toInt(2)
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println("The power consumption of the submarine?: ${part1(input)}")
    println("life support rating: ${part2(input)}")
}


