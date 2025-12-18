package day09

import common.readInputText
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day09Test {
  private val part1 = Day09.Part1()
  private val part2 = Day09.Part2()

  @Test
  fun `part 1 sample input`() {
    val input = readInputText("day09-sample.txt")
    part1.solve(input).shouldBe(50L)
  }

  @Test
  fun `part 1 input`() {
    val input = readInputText("day09-input.txt")
    part1.solve(input).shouldBe(4774877510L)
  }

  @Test
  fun `part 2 sample input`() {
    val input = readInputText("day09-sample.txt")
    part2.solve(input).shouldBe(24L)
  }

  @Test
  fun `part 2 input`() {
    val input = readInputText("day09-input.txt")
    part2.solve(input).shouldBe(1560475800L)
  }
}
