package day10

import common.readInputText
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day10Test {
  private val part1 = Day10.Part1()
  private val part2 = Day10.Part2()

  @Test
  fun `part 1 sample input - machine 1`() {
    part1.solve("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}").shouldBe(2)
  }

  @Test
  fun `part 1 sample input - machine 2`() {
    part1.solve("[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}").shouldBe(3)
  }

  @Test
  fun `part 1 sample input - machine 3`() {
    part1.solve("[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}").shouldBe(2)
  }

  @Test
  fun `part 1 sample input`() {
    val input = readInputText("day10-sample.txt")
    part1.solve(input).shouldBe(7)
  }

  @Test
  fun `part 1 input`() {
    val input = readInputText("day10-input.txt")
    part1.solve(input).shouldBe(469)
  }

  @Test
  fun `part 2 sample input - machine 1`() {
    part2.solve("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}").shouldBe(10)
  }

  @Test
  fun `part 2 sample input - machine 2`() {
    part2.solve("[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}").shouldBe(12)
  }

  @Test
  fun `part 2 sample input - machine 3`() {
    part2.solve("[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}").shouldBe(11)
  }

  @Test
  fun `part 2 sample input`() {
    val input = readInputText("day10-sample.txt")
    part2.solve(input).shouldBe(33)
  }

  @Test
  fun `part 2 input`() {
    val input = readInputText("day10-input.txt")
    part2.solve(input).shouldBe(19293)
  }
}
