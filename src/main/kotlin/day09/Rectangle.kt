package day09

import common.Point2D
import kotlin.math.abs

/**
 * Rectangle with opposite corners [p1] & [p2].
 *
 * Somewhat atypical as a degenerate rectangle `Rectangle(p1=(7,3), p2=(2,3))` (a line) is
 * considered to have area = 6
 */
data class Rectangle(val p1: Point2D, val p2: Point2D) {

  val width: Int = abs(p2.x - p1.x) + 1
  val height: Int = abs(p2.y - p1.y) + 1

  val minX: Int = minOf(p1.x, p2.x)
  val maxX: Int = maxOf(p1.x, p2.x)
  val minY: Int = minOf(p1.y, p2.y)
  val maxY: Int = maxOf(p1.y, p2.y)

  val area: Long = width.toLong() * height.toLong()

  override fun toString(): String = "Rectangle($p1, $p2, area = $area)"
  
  // Overridden so that R(p1, p2) == R(p2, p1)
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Rectangle) return false

    return (p1 == other.p1 && p2 == other.p2) || (p1 == other.p2 && p2 == other.p1)
  }

  // Overridden so that R(p1, p2) == R(p2, p1)
  override fun hashCode(): Int {
    return 31 * (p1.hashCode() + p2.hashCode())
  }
}
