package day06

import common.readInputText
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class Day06Test {
  private val part1 = Day06.Part1()
  private val part2 = Day06.Part2()

  @Test
  fun `part 1 sample input`() {
    val input = readInputText("day06-sample.txt")
    part1.solve(input).shouldBe(4277556.toBigInteger())
  }

  @Test
  fun `part 1 input`() {
    val input = readInputText("day06-input.txt")
    part1.solve(input).shouldBe(5873191732773.toBigInteger())
  }

  @Test
  fun `part 2 sample input`() {
    val input = readInputText("day06-sample.txt")
    part2.solve(input).shouldBe(3263827.toBigInteger())
  }

  @Test
  fun `part 2 input`() {
    val input = readInputText("day06-input.txt")
    part2.solve(input).shouldBe(11386445308378.toBigInteger())
  }
}
