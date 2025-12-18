package day09

import common.Point2D

data class Line(val p1: Point2D, val p2: Point2D) {
  init {
    require(p1.x == p2.x || p1.y == p1.y) {
      "Expected points to have either the same x coordinate or the same y coordinate. Points: $p1, $p2"
    }
  }

  val isVertical: Boolean = p1.x == p2.x
  val isHorizontal: Boolean = p1.y == p2.y

  val minX: Int
    get() = minOf(p1.x, p2.x)

  val maxX: Int
    get() = maxOf(p1.x, p2.x)

  val minY: Int
    get() = minOf(p1.y, p2.y)

  val maxY: Int
    get() = maxOf(p1.y, p2.y)

  override fun toString(): String = "Line($p1, $p2)"
}
