package day03

internal typealias Joltage = Int

internal typealias Battery = List<Joltage>

/** https://adventofcode.com/2025/day/3 */
class Day03 {

  class Part1 {
    fun solve(input: String): Long {
      val batteries: List<Battery> = parseInput(input)
      return batteries.sumOf { maxJoltage(it) }
    }

    private fun maxJoltage(battery: Battery): Long {
      // First number = the max excluding the last element
      val (firstDigitIndex, firstDigit) = battery.dropLast(1).withIndex().maxBy { it.value }
      val secondDigit: Joltage = battery.drop(firstDigitIndex + 1).max()
      return "${firstDigit}${secondDigit}".toLong()
    }
  }

  class Part2 {
    fun solve(input: String): Long {
      val batteries: List<Battery> = parseInput(input)
      return batteries.sumOf { battery -> maxJoltage(battery, numBatteriesToEnable = 12) }
    }

    /**
     * Now, you need to make the largest joltage by turning on exactly twelve batteries within each
     * bank.
     */
    private fun maxJoltage(battery: Battery, numBatteriesToEnable: Int): Long {
      val batteriesToEnable = ArrayDeque<IndexedValue<Joltage>>()

      for (batteryNumber in 1..numBatteriesToEnable) {
        val max =
          battery
            .withIndex()
            .toList()
            .drop(batteriesToEnable.lastOrNull()?.index?.plus(1) ?: 0)
            .dropLast(numBatteriesToEnable - batteryNumber)
            .maxBy { it.value }
        batteriesToEnable.addLast(max)
      }
      return batteriesToEnable.map { it.value }.joinToString("").toLong()
    }
  }
}

private fun parseInput(input: String): List<Battery> =
  input.lines().map { line -> line.map { it.digitToInt() } }
