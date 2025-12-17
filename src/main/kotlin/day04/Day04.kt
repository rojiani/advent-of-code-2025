package day04

import common.GridCoordinate

/** https://adventofcode.com/2025/day/4 */
class Day04 {

  class Part1 {
    fun solve(input: String): Int {
      val grid: List<List<Char>> = parseInput(input)

      var accessibleByForklift = 0
      for (row in grid.indices) {
        for (col in grid[row].indices) {
          if (grid[row][col] == ROLL_OF_PAPER && isAccessible(grid, GridCoordinate(row, col))) {
            accessibleByForklift++
          }
        }
      }
      return accessibleByForklift
    }
  }

  class Part2 {
    fun solve(input: String): Int {
      val startingGrid: List<List<Char>> = parseInput(input)

      var grid = startingGrid
      var totalRemoved = 0
      do {
        val (newGrid, removedPaperRolls) = removeAccessible(grid)
        totalRemoved += removedPaperRolls.size
        grid = newGrid
      } while (removedPaperRolls.isNotEmpty())

      return totalRemoved
    }

    private fun removeAccessible(grid: List<List<Char>>): RemovalResult {
      var removedPaperRolls = mutableSetOf<GridCoordinate>()
      for (row in grid.indices) {
        for (col in grid[row].indices) {
          if (grid[row][col] == ROLL_OF_PAPER) {
            val coordinate = GridCoordinate(row, col)
            if (isAccessible(grid, coordinate)) {
              removedPaperRolls += coordinate
            }
          }
        }
      }

      // Create new grid
      val newGrid: List<MutableList<Char>> = grid.map { row -> row.toCharArray().toMutableList() }
      for (row in grid.indices) {
        for (col in grid[row].indices) {
          if (GridCoordinate(row, col) in removedPaperRolls) {
            newGrid[row][col] = EMPTY
          }
        }
      }

      return RemovalResult(newGrid, removedPaperRolls)
    }

    /** Result of removing all rolls of paper that are accessible by forklift once */
    data class RemovalResult(val newGrid: List<List<Char>>, val removed: Set<GridCoordinate>)
  }
}

// The forklifts can only access a roll of paper if there are fewer than four rolls of paper in
// the eight adjacent positions
private fun isAccessible(grid: List<List<Char>>, paperCoordinate: GridCoordinate): Boolean {
  return paperCoordinate.validNeighbors(grid).count { coordinate ->
    grid[coordinate.row][coordinate.col] == ROLL_OF_PAPER
  } < 4
}

private fun parseInput(input: String): List<List<Char>> {
  val lines = input.lines()
  return buildList {
    for (line in lines) {
      val row = line.toList()
      add(row)
    }
  }
}

const val ROLL_OF_PAPER = '@'
const val EMPTY = '.'
