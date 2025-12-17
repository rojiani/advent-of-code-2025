package day05

typealias IngredientId = Long

typealias IngredientRange = LongRange

/** https://adventofcode.com/2025/day/5 */
class Day05 {

  class Part1 {
    fun solve(input: String): Int {
      val (ingredientRanges, ingredientIds) = parseInput(input)

      val freshIngredientRanges: List<IngredientRange> = mergeRanges(ingredientRanges)

      return ingredientIds.count { ingredientId ->
        freshIngredientRanges.any { ingredientId in it }
      }
    }
  }

  class Part2 {
    fun solve(input: String): Long {
      val (ingredientRanges, _) = parseInput(input)
      val freshIngredientRanges: List<IngredientRange> = mergeRanges(ingredientRanges)
      return freshIngredientRanges.sumOf { range -> range.last - range.first + 1 }
    }
  }
}

internal fun mergeRanges(ingredientRanges: List<IngredientRange>): List<IngredientRange> {
  val sortedRanges = ingredientRanges.sortedBy { it.first }
  val mergedRanges = mutableListOf<IngredientRange>()

  for (range in sortedRanges) {
    if (mergedRanges.isEmpty() || !range.overlapsWith(mergedRanges.last())) {
      mergedRanges += range
    } else {
      val lastRange = mergedRanges.removeLast()
      mergedRanges +=
        IngredientRange(minOf(range.first, lastRange.first), maxOf(range.last, lastRange.last))
    }
  }

  return mergedRanges
}

private fun IngredientRange.overlapsWith(other: IngredientRange): Boolean =
  start in other.first..other.last || last in other.first..other.last

private fun parseInput(input: String): IngredientsDatabase {
  val lines = input.lines()
  val ingredientRangeLines = lines.filter { '-' in it }
  val ingredientIdLines = lines.drop(ingredientRangeLines.size + 1)

  val ingredientRanges: List<IngredientRange> = buildList {
    for (line in ingredientRangeLines) {
      val (start, end) = line.split('-').map { it.toLong() }
      add(IngredientRange(start, end))
    }
  }

  val ingredientIds: List<IngredientId> = ingredientIdLines.map { it.toLong() }

  return IngredientsDatabase(ingredientRanges, ingredientIds)
}

data class IngredientsDatabase(
  val ingredientRanges: List<IngredientRange>,
  val ingredientIds: List<IngredientId>,
)
