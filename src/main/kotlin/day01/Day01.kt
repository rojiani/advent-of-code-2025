package day01

import kotlin.math.abs

class Day01 {

  class Part1 {
    fun solve(input: String): Int {
      val rotations = parseInput(input)

      var timesAtZero = 0
      var position = START_POSITION
      for (rotation in rotations) {
        val remaining = rotation.distance % TOTAL_POSITIONS

        position =
          when (rotation.direction) {
            Direction.LEFT ->
              when {
                remaining > position -> TOTAL_POSITIONS - abs(position - remaining)
                else -> position - remaining
              }
            Direction.RIGHT ->
              when {
                remaining >= (TOTAL_POSITIONS - position) ->
                  (position + remaining) % TOTAL_POSITIONS
                else -> position + remaining
              }
          }
        if (position == 0) {
          timesAtZero++
        }
      }

      return timesAtZero
    }
  }

  class Part2 {
    fun solve(input: String): Int {
      val rotations = parseInput(input)
      var position = START_POSITION
      var timesAtOrCrossedZero = 0
      for (rotation in rotations) {
        val rotationResult = rotate(position, rotation)
        position = rotationResult.endPosition
        timesAtOrCrossedZero += rotationResult.timesAtOrCrossedZero
      }

      return timesAtOrCrossedZero
    }

    data class RotationResult(val endPosition: Int, val timesAtOrCrossedZero: Int) {
      init {
        check(endPosition in 0..<TOTAL_POSITIONS) {
          "Invalid end position: $endPosition should be in [0, $TOTAL_POSITIONS)"
        }
      }
    }

    private fun rotate(position: Int, rotation: Rotation): RotationResult {
      val finalPosition =
        when (rotation.direction) {
          Direction.LEFT -> (position - rotation.distance).mod(TOTAL_POSITIONS)
          Direction.RIGHT -> (position + rotation.distance).mod(TOTAL_POSITIONS)
        }

      val fullRotations = rotation.distance / TOTAL_POSITIONS
      val remainderDistance = rotation.distance.mod(TOTAL_POSITIONS)
      val remainderZeroCross =
        when (position) {
          // If the position is at zero, then it's already been counted.
          0 -> 0
          else ->
            when (rotation.direction) {
              Direction.LEFT -> if (remainderDistance >= position) 1 else 0
              Direction.RIGHT -> if (remainderDistance >= TOTAL_POSITIONS - position) 1 else 0
            }
        }

      return RotationResult(finalPosition, fullRotations + remainderZeroCross)
    }
  }
}

private fun parseInput(text: String): List<Rotation> {
  return text
    .lines()
    .map { it.trim() }
    .filter { it.isNotEmpty() }
    .map { line ->
      val direction =
        when (line.first()) {
          'L' -> Direction.LEFT
          'R' -> Direction.RIGHT
          else -> throw IllegalArgumentException("Invalid direction char: ${line.first()}")
        }
      val amount = line.drop(1).toInt()
      Rotation(direction, amount)
    }
}

const val START_POSITION = 50
const val TOTAL_POSITIONS = 100

data class Rotation(val direction: Direction, val distance: Int)

enum class Direction {
  LEFT,
  RIGHT,
}
