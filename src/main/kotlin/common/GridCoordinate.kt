package common

/** A `[row][column]` coordinate in a 2D matrix */
data class GridCoordinate(val row: Int, val col: Int) {

  fun <T> isValid(grid: List<List<T>>): Boolean = row in grid.indices && col in grid[row].indices

  /** Return the adjacent cells (up to 8) that are within the bounds of the [grid] */
  fun <T> validNeighbors(grid: List<List<T>>): Set<GridCoordinate> =
    setOf(
        GridCoordinate(row - 1, col - 1),
        GridCoordinate(row - 1, col),
        GridCoordinate(row - 1, col + 1),
        GridCoordinate(row, col - 1),
        GridCoordinate(row, col + 1),
        GridCoordinate(row + 1, col - 1),
        GridCoordinate(row + 1, col),
        GridCoordinate(row + 1, col + 1),
      )
      .filter { it.isValid(grid) }
      .toSet()
}
