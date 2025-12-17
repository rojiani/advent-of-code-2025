package day03

import common.readInputText
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import java.math.BigInteger
import kotlin.test.Test

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

class SampleInputIndividualRangeTest :
  FunSpec({
    val part1 = Day03.Part1()

    data class SampleInputTestCase(val input: String, val expectedSum: BigInteger)

    context("part 1 sample input examples") {
      withData(
        SampleInputTestCase("11-22", (11 + 22).toBigInteger()),
        SampleInputTestCase("95-115", 99.toBigInteger()),
        SampleInputTestCase("998-1012", 1010.toBigInteger()),
        SampleInputTestCase("1188511880-1188511890", 1188511885.toBigInteger()),
        SampleInputTestCase("222220-222224", 222222.toBigInteger()),
        SampleInputTestCase("1698522-1698528", BigInteger.ZERO),
        SampleInputTestCase("446443-446449", 446446.toBigInteger()),
        SampleInputTestCase("38593856-38593862", 38593859.toBigInteger()),
      ) { (input, expected) ->
        part1.solve(input) shouldBe expected
      }
    }
  })
