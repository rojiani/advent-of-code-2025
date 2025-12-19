package day04

import common.readInputText
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day04Test {
  private val part1 = Day04.Part1()
  private val part2 = Day04.Part2()

  @Test
  fun `part 1 sample input`() {
    val input = readInputText("day04-sample.txt")
    part1.solve(input).shouldBe(13)
  }

  @Test
  fun `part 1 input`() {
    val input = readInputText("day04-input.txt")
    part1.solve(input).shouldBe(1543)
  }

  @Test
  fun `part 2 sample input`() {
    val input = readInputText("day04-sample.txt")
    part2.solve(input).shouldBe(43)
  }

  @Test
  fun `part 2 input`() {
    val input = readInputText("day04-input.txt")
    part2.solve(input).shouldBe(9038)
  }
}
