package day02

import java.math.BigInteger

/** https://adventofcode.com/2025/day/2 */
class Day02 {

  class Part1 {
    fun solve(input: String): BigInteger {
      val ranges: List<LongRange> = parseInput(input)

      val invalidIds = ranges.flatMap { range -> findInvalidIds(range) }
      return sumIds(invalidIds)
    }

    private fun findInvalidIds(range: LongRange): List<Long> =
      range.filter { id -> isInvalidId(id) }

    private fun isInvalidId(id: Long): Boolean {
      val digits = id.toString()

      val firstHalf = digits.take(digits.length / 2)
      val secondHalf = digits.drop(digits.length / 2)
      return firstHalf == secondHalf
    }
  }

  class Part2 {
    fun solve(input: String): BigInteger {
      val ranges: List<LongRange> = parseInput(input)

      val invalidIds = ranges.flatMap { range -> findInvalidIds(range) }
      return sumIds(invalidIds)
    }

    private fun findInvalidIds(range: LongRange): List<Long> =
      range.filter { id -> isInvalidId(id) }

    private fun isInvalidId(id: Long): Boolean {
      val digits = id.toString()

      // Check windows of increasing size
      for (chunkSize in 1..(digits.length / 2)) {
        val chunks: List<String> = digits.chunked(size = chunkSize)
        if (chunks.size > 1 && chunks.all { it == chunks.first() }) {
          return true
        }
      }
      return false
    }
  }
}

private fun parseInput(text: String): List<LongRange> =
  text
    .split(",")
    .map { it.trim() }
    .filter { it.isNotEmpty() }
    .map { rangeString ->
      val (start, end) = rangeString.split("-").map { it.toLong() }
      start..end
    }

private fun sumIds(ids: List<Long>): BigInteger =
  ids.map { it.toBigInteger() }.fold(BigInteger.ZERO) { acc, id -> acc + id }
