package day07

import common.readInputText
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day07Test {
  private val part1 = Day07.Part1()
  private val part2 = Day07.Part2()

  @Test
  fun `part 1 sample input`() {
    val input = readInputText("day07-sample.txt")
    part1.solve(input).shouldBe(21L)
  }

  @Test
  fun `part 1 input`() {
    val input = readInputText("day07-input.txt")
    part1.solve(input).shouldBe(1600L)
  }

  @Test
  fun `part 2 sample input`() {
    val input = readInputText("day07-sample.txt")
    part2.solve(input).shouldBe(40L)
  }

  @Test
  fun `part 2 input`() {
    val input = readInputText("day07-input.txt")
    part2.solve(input).shouldBe(8632253783011L)
  }
}
