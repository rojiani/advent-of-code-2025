package day12

/** https://adventofcode.com/2025/day/12 */
class Day12 {
  fun solve(input: String): Int {
    val treeProblems: List<TreeProblem> = parseInput(input).treeProblems
    return treeProblems.count { canDefinitelyFitAllPresents(it) }
  }

  private fun canDefinitelyFitAllPresents(treeProblem: TreeProblem): Boolean {
    val (width, length) = treeProblem.regionUnderTree
    val numberOfPresentsThatDefinitelyFit = (width / PRESENT_WIDTH) * (length / PRESENT_LENGTH)
    return numberOfPresentsThatDefinitelyFit >= treeProblem.totalPresents
  }
}

private fun parseInput(input: String): ParsedInput {
  val groups: List<List<String>> =
    input.trim().split(Regex("\n{2,}")).map { block -> block.lines() }.filter { it.isNotEmpty() }

  val presents = groups.dropLast(1).map { Present.fromLines(it.drop(1)) }

  val treeProblemLines = groups.takeLast(1).flatten()
  val treeProblems =
    treeProblemLines.map { line ->
      val dimensionsString = line.substringBefore(':')
      val regionUnderTree =
        RegionUnderTree(
          width = dimensionsString.substringBefore('x').toLong(),
          length = dimensionsString.substringAfter('x').toLong(),
        )
      val presentCounts = line.substringAfter(':').trim().split(' ').map { it.trim().toInt() }
      TreeProblem(regionUnderTree, presentCounts)
    }

  return ParsedInput(presents, treeProblems)
}

data class ParsedInput(val presents: List<Present>, val treeProblems: List<TreeProblem>)

data class Present(val grid: List<List<Char>>, val areaFilled: Int) {
  companion object {
    fun fromLines(gridLines: List<String>): Present {
      val grid = gridLines.map { line -> line.trim().toCharArray().toList() }
      var areaFilled = 0
      for (row in grid.indices) {
        for (column in grid[row].indices) {
          if (grid[row][column] == '#') {
            areaFilled++
          }
        }
      }
      return Present(grid, areaFilled)
    }
  }
}

data class TreeProblem(val regionUnderTree: RegionUnderTree, val presentCounts: List<Int>) {
  val totalPresents = presentCounts.sum()
}

data class RegionUnderTree(val width: Long, val length: Long)

// Treat all presents as 3x3, ignore their actual shapes.
const val PRESENT_LENGTH = 3L
const val PRESENT_WIDTH = 3L

