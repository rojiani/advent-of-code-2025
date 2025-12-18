package day02

import common.readInputText
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class Day02Test {
  private val part1 = Day02.Part1()
  private val part2 = Day02.Part2()

  @Test
  fun `part 1 sample input`() {
    val input = readInputText("day02-sample.txt")
    part1.solve(input).shouldBe(1227775554.toBigInteger())
  }

  @Test
  fun `part 1 input`() {
    val input = readInputText("day02-input.txt")
    part1.solve(input).shouldBe(37314786486.toBigInteger())
  }

  @Test
  fun `part 2 sample input`() {
    val input = readInputText("day02-sample.txt")
    part2.solve(input).shouldBe(4174379265.toBigInteger())
  }

  @Test
  fun `part 2 input`() {
    val input = readInputText("day02-input.txt")
    part2.solve(input).shouldBe(47477053982.toBigInteger())
  }
}
