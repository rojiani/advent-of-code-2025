package day12

import common.readInputText
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day12Test {

  @Test
  fun `problem input`() {
    val input = readInputText("day12-input.txt")
    Day12().solve(input) shouldBe 469
  }
}
