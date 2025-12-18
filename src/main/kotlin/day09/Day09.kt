package day09

import common.Point2D

/** https://adventofcode.com/2025/day/9 */
class Day09 {
  class Part1 {
    fun solve(input: String): Long {
      val redTiles = parseInput(input)
      val rectangles: Set<Rectangle> = getRectangles(redTiles)
      return rectangles.maxBy { it.area }.area
    }
  }

  class Part2 {
    fun solve(input: String): Long {
      val redTiles: List<Point2D> = parseInput(input)
      val borderLines: Set<Line> = createBorderLines(redTiles)
      val rectangles: List<Rectangle> = getRectangles(redTiles).sortedByDescending { it.area }

      return rectangles.first { rectangle -> isWithinBorders(rectangle, borderLines) }.area
    }

    private fun isWithinBorders(rectangle: Rectangle, borderLines: Set<Line>): Boolean =
      borderLines.none { line -> doesLineIntersectRectangle(line, rectangle) }

    private fun createBorderLines(redTiles: List<Point2D>): Set<Line> {
      val borderLines = mutableSetOf<Line>()
      val redTilePairs: List<Pair<Point2D, Point2D>> =
        redTiles.zipWithNext() + Pair(redTiles.last(), redTiles.first())

      for (pair in redTilePairs) {
        when {
          // Vertical border tiles
          pair.first.x == pair.second.x -> {
            val x = pair.first.x
            val minY = minOf(pair.first.y, pair.second.y)
            val maxY = maxOf(pair.first.y, pair.second.y)
            borderLines += Line(Point2D(x, minY), Point2D(x, maxY))
          }
          // Horizontal border tiles
          pair.first.y == pair.second.y -> {
            val y = pair.first.y
            val minX = minOf(pair.first.x, pair.second.x)
            val maxX = maxOf(pair.first.x, pair.second.x)
            borderLines += Line(Point2D(minX, y), Point2D(maxX, y))
          }
          else ->
            throw IllegalArgumentException(
              "Expected pair to have the same x or y coordinate: $pair"
            )
        }
      }

      return borderLines
    }

    /** Returns true if the Line intersects the Rectangle, *excluding the rectangle's border* */
    private fun doesLineIntersectRectangle(line: Line, rectangle: Rectangle): Boolean {
      val xRangeExcludingBorder = rectangle.minX + 1..<rectangle.maxX
      val yRangeExcludingBorder = rectangle.minY + 1..<rectangle.maxY
      val lineIsWithinYRangeExcludingBorder =
        line.minY in yRangeExcludingBorder || line.maxY in yRangeExcludingBorder
      val lineIsWithinXRangeExcludingBorder =
        line.minX in xRangeExcludingBorder || line.maxX in xRangeExcludingBorder

      return when {
        line.isVertical -> {
          // No overlap if the line is to the left or right of the rectangle, or if it is on the
          // edges
          // of the rectangle
          if (line.minY <= rectangle.minY && line.maxY >= rectangle.maxY) {
            // Vertical bisection
            lineIsWithinXRangeExcludingBorder
          } else {
            lineIsWithinXRangeExcludingBorder && lineIsWithinYRangeExcludingBorder
          }
        }
        line.isHorizontal -> {
          // No overlap if the line is above or below the rectangle, or if it is on the edges
          // of the rectangle
          if (line.minX <= rectangle.minX && line.maxX >= rectangle.maxX) {
            lineIsWithinYRangeExcludingBorder
          } else {
            lineIsWithinYRangeExcludingBorder && lineIsWithinXRangeExcludingBorder
          }
        }
        else -> throw IllegalArgumentException("Not needed for this problem")
      }
    }
  }
}

private fun parseInput(input: String) =
  input.lines().map {
    val (first, second) = it.trim().split(',')
    Point2D(x = first.toInt(), y = second.toInt())
  }

/**
 * Get the set of distinct rectangles (including only one of R(p1, p2) and R(p2, p1)) formed by red
 * tiles
 */
private fun getRectangles(points: List<Point2D>): Set<Rectangle> {
  return points
    .flatMapIndexed { index, p1 -> points.drop(index + 1).map { p2 -> Rectangle(p1, p2) } }
    .toSet()
}

const val RED_TILE = '#'
