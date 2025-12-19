package day03

import common.readInputText
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03Test {
  private val part1 = Day03.Part1()
  private val part2 = Day03.Part2()

  @Test
  fun `part 1 sample input`() {
    val input = readInputText("day03-sample.txt")
    part1.solve(input).shouldBe(357)
  }

  @Test
  fun `part 1 input`() {
    val input = readInputText("day03-input.txt")
    part1.solve(input).shouldBe(17034L)
  }

  @Test
  fun `part 2 sample input`() {
    val input = readInputText("day03-sample.txt")
    part2.solve(input).shouldBe(3121910778619L)
  }

  @Test
  fun `part 2 input`() {
    val input = readInputText("day03-input.txt")
    part2.solve(input).shouldBe(168798209663590L)
  }
}
