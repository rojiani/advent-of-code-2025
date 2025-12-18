package day07

import common.GridCoordinate

class Day07 {
  class Part1 {
    fun solve(input: String): Long {
      val grid: Array<CharArray> = parseInput(input)

      val start = findStart(grid)
      // Extend beam to 1st Splitter
      var coordinate = GridCoordinate(start.row + 1, start.col)
      while (coordinate.isValid(grid) && grid[coordinate.row][coordinate.col] != SPLITTER) {
        grid[coordinate.row][coordinate.col] = BEAM
        coordinate = GridCoordinate(coordinate.row + 1, coordinate.col)
      }

      var splits = 0L
      for (r in grid.indices) {
        for (c in grid[r].indices) {
          val current = GridCoordinate(r, c)
          // Splitter with incoming beam - set L & R to beam
          if (grid[r][c] == SPLITTER && grid[r - 1][c] == BEAM) {
            if (current.left.isValid(grid)) {
              grid[r][c - 1] = BEAM
            }
            if (current.right.isValid(grid)) {
              grid[r][c + 1] = BEAM
            }
            splits++
            // Extend Beam downwards
          } else if (current.up.isValid(grid) && grid[r - 1][c] == BEAM) {
            grid[r][c] = BEAM
          }
        }
      }

      return splits
    }
  }

  class Part2 {
    fun solve(input: String): Long {
      val grid: Array<CharArray> = parseInput(input)
      val start = findStart(grid)
      return countTimelines(start, grid, mutableMapOf())
    }

    private fun countTimelines(
      current: GridCoordinate,
      grid: Array<CharArray>,
      timelinesFromCoordinate: MutableMap<GridCoordinate, Long>,
    ): Long =
      when {
        current in timelinesFromCoordinate -> timelinesFromCoordinate.getValue(current)

        // outside of bounds on left or right
        current.col !in grid[0].indices -> timelinesFromCoordinate.getOrPut(current) { 0 }

        // reached the end
        current.row == grid.lastIndex -> timelinesFromCoordinate.getOrPut(current) { 1 }

        else ->
          when (grid[current.row][current.col]) {
            // Count timelines from extending L & R
            SPLITTER ->
              timelinesFromCoordinate.getOrPut(current) {
                countTimelines(current = current.left, grid, timelinesFromCoordinate) +
                  countTimelines(current = current.right, grid, timelinesFromCoordinate)
              }
            // Extend downwards
            else ->
              timelinesFromCoordinate.getOrPut(current) {
                countTimelines(
                  current = GridCoordinate(current.row + 1, current.col),
                  grid,
                  timelinesFromCoordinate,
                )
              }
          }
      }
  }
}

private fun parseInput(inputText: String): Array<CharArray> =
  inputText.lines().map { line -> line.toCharArray() }.toTypedArray()

const val START = 'S'
const val SPLITTER = '^'
const val BEAM = '|'

private fun findStart(grid: Array<CharArray>): GridCoordinate {
  for (r in grid.indices) {
    for (c in grid[r].indices) {
      if (grid[r][c] == START) {
        return GridCoordinate(r, c)
      }
    }
  }
  throw IllegalArgumentException("No starting point found in grid")
}
