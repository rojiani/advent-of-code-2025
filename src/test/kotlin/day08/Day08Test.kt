package day08

import common.readInputText
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class Day08Test {
  private val part1 = Day08.Part1()
  private val part2 = Day08.Part2()

  @Test
  fun `part 1 sample input`() {
    val input = readInputText("day08-sample.txt")
    part1.solve(input, distancesToProcess = 10).shouldBe(40L)
  }

  @Test
  fun `part 1 input`() {
    val input = readInputText("day08-input.txt")
    part1.solve(input, distancesToProcess = 1000).shouldBe(330786L)
  }

  @Disabled("TODO")
  @Test
  fun `part 2 sample input`() {
    val input = readInputText("day08-sample.txt")
    part2.solve(input).shouldBe(0L)
  }

  @Disabled("TODO")
  @Test
  fun `part 2 input`() {
    val input = readInputText("day08-input.txt")
    part2.solve(input).shouldBe(0L)
  }
}
