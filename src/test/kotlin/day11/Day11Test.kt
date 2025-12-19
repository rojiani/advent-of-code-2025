package day11

import common.readInputText
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day11Test {
  private val part1 = Day11.Part1()
  private val part2 = Day11.Part2()

  @Test
  fun `part 1 sample input`() {
    val input = readInputText("day11-part1-sample.txt")
    part1.solve(input).shouldBe(5L)
  }

  @Test
  fun `part 1 input`() {
    val input = readInputText("day11-input.txt")
    part1.solve(input).shouldBe(494L)
  }

  @Test
  fun `part 2 sample input`() {
    val input = readInputText("day11-part2-sample.txt")
    part2.solve(input).shouldBe(2L)
  }

  @Test
  fun `part 2 input`() {
    val input = readInputText("day11-input.txt")
    part2.solve(input).shouldBe(296006754704850L)
  }
}
