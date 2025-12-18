package day08

import common.Point3D
import common.distance

typealias Circuit = Set<Point3D>

/** https://adventofcode.com/2025/day/8 */
class Day08 {
  class Part1 {
    fun solve(input: String, distancesToProcess: Int): Long {
      val points = parseInput(input)
      val sortedDistances: ArrayDeque<DistanceBetweenPoints> =
        ArrayDeque(sortedDistanceBetweenPoints(points))
      // Put each Junction Box in a circuit
      val circuits = mutableSetOf<Circuit>()
      val pointToCircuitMap = mutableMapOf<Point3D, Circuit>()
      for (point in points) {
        val circuit = setOf(point)
        circuits += circuit
        pointToCircuitMap[point] = circuit
      }

      var distancesProcessed = 0
      while (distancesProcessed < distancesToProcess) {
        val closestPair = sortedDistances.removeFirst()
        val (p1, p2) = closestPair.points.first() to closestPair.points.last()
        distancesProcessed++

        // if both are in the same circuit, do nothing
        if (pointToCircuitMap.getValue(p1) == pointToCircuitMap.getValue(p2)) {
          continue
        }

        // otherwise, merge them into the larger of the 2 circuits
        val p1Circuit = pointToCircuitMap.getValue(p1)
        val p2Circuit = pointToCircuitMap.getValue(p2)
        val pointInLargerCircuit = if (p1Circuit.size >= p2Circuit.size) p1 else p2
        val pointInSmallerCircuit = if (p1Circuit.size < p2Circuit.size) p1 else p2

        val smallerCircuit = pointToCircuitMap.getValue(pointInSmallerCircuit)
        val largerCircuit = pointToCircuitMap.getValue(pointInLargerCircuit)
        pointToCircuitMap -= pointInLargerCircuit
        pointToCircuitMap -= pointInSmallerCircuit
        circuits -= smallerCircuit
        circuits -= largerCircuit
        val mergedCircuit = largerCircuit + smallerCircuit
        circuits += mergedCircuit

        for (point in mergedCircuit) {
          pointToCircuitMap[point] = mergedCircuit
        }
      }

      // what do you get if you multiply together the sizes of the three largest circuits?
      return circuits
        .sortedByDescending { it.size }
        .take(3)
        .fold(1) { acc, circuit -> acc * circuit.size }
    }
  }

  class Part2 {
    fun solve(input: String): Long {
      val points = parseInput(input)
      val sortedDistances: ArrayDeque<DistanceBetweenPoints> =
        ArrayDeque(sortedDistanceBetweenPoints(points))
      // Put each Junction Box in a circuit
      val circuits = mutableSetOf<Circuit>()
      val pointToCircuitMap = mutableMapOf<Point3D, Circuit>()
      for (point in points) {
        val circuit = setOf(point)
        circuits += circuit
        pointToCircuitMap[point] = circuit
      }

      var lastConnectedPoints: Pair<Point3D, Point3D>? = null
      while (circuits.size > 1) {
        val closestPair = sortedDistances.removeFirst()
        val (p1, p2) = closestPair.points.first() to closestPair.points.last()

        // if both are in the same circuit, do nothing
        if (pointToCircuitMap.getValue(p1) == pointToCircuitMap.getValue(p2)) {
          continue
        }

        // otherwise, merge them into the larger of the 2 circuits
        val p1Circuit = pointToCircuitMap.getValue(p1)
        val p2Circuit = pointToCircuitMap.getValue(p2)
        val pointInLargerCircuit = if (p1Circuit.size >= p2Circuit.size) p1 else p2
        val pointInSmallerCircuit = if (p1Circuit.size < p2Circuit.size) p1 else p2

        val smallerCircuit = pointToCircuitMap.getValue(pointInSmallerCircuit)
        val largerCircuit = pointToCircuitMap.getValue(pointInLargerCircuit)
        pointToCircuitMap -= pointInLargerCircuit
        pointToCircuitMap -= pointInSmallerCircuit
        circuits -= smallerCircuit
        circuits -= largerCircuit
        val mergedCircuit = largerCircuit + smallerCircuit
        circuits += mergedCircuit
        lastConnectedPoints = p1 to p2

        for (point in mergedCircuit) {
          pointToCircuitMap[point] = mergedCircuit
        }
      }

      // What do you get if you multiply together the X coordinates of the last two junction boxes
      // you need to connect?
      val lastConnected = requireNotNull(lastConnectedPoints)
      return lastConnected.first.x.toLong() * lastConnected.second.x.toLong()
    }
  }
}

private fun sortedDistanceBetweenPoints(points: List<Point3D>): List<DistanceBetweenPoints> {
  val pairs = mutableListOf<Set<Point3D>>()
  for (p in points) {
    for (q in points) {
      if (p == q) continue
      pairs += setOf(p, q)
    }
  }
  return pairs
    .map { pointSet ->
      val (p, q) = pointSet.first() to pointSet.last()
      DistanceBetweenPoints(distance = distance(p, q), points = setOf(p, q))
    }
    // Include only one of (p, q) and (q, p)
    .distinctBy { it.points }
    .sortedBy { it.distance }
}

data class DistanceBetweenPoints(val distance: Double, val points: Set<Point3D>)

private fun parseInput(inputText: String): List<Point3D> =
  inputText.lines().map {
    val digitStrings = it.trim().split(',')
    check(digitStrings.size == 3)
    Point3D(x = digitStrings[0].toInt(), y = digitStrings[1].toInt(), z = digitStrings[2].toInt())
  }
