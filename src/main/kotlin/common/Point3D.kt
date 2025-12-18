package common

import kotlin.math.pow
import kotlin.math.sqrt

data class Point3D(val x: Int, val y: Int, val z: Int) {
  override fun toString(): String = "Point3D($x, $y, $z)"
}

/** https://en.wikipedia.org/wiki/Euclidean_distance#Higher_dimensions */
fun distance(p1: Point3D, p2: Point3D): Double {
  return sqrt(
    (p1.x - p2.x).toDouble().pow(2) +
      (p1.y - p2.y).toDouble().pow(2) +
      (p1.z - p2.z).toDouble().pow(2)
  )
}
