package day06

import java.math.BigInteger
import kotlin.collections.fold

/** https://adventofcode.com/2025/day/6 */
class Day06 {

  class Part1 {
    fun solve(input: String): BigInteger {
      val mathProblems = parseInput(input)
      return mathProblems.fold(BigInteger.ZERO) { sum, problem -> sum + problem.evaluate() }
    }

    private fun parseInput(input: String): List<MathProblem> {
      val lines = input.lines()
      val operandLines: List<List<Int>> =
        lines.dropLast(1).map { line ->
          line.split("""\s+""".toRegex()).filter { it.isNotEmpty() }.map { it.toInt() }
        }
      val operations =
        lines
          .last()
          .split("""\s+""".toRegex())
          .filter { it.isNotEmpty() }
          .map { operationString -> operationString.toCharArray().single() }
          .map(Operation::fromSymbol)

      check(operandLines.all { it.size == operations.size })

      val mathProblems = buildList {
        for (problemIndex in operandLines[0].indices) {
          val operands = buildList {
            for (operandLineIndex in operandLines.indices) {
              add(operandLines[operandLineIndex][problemIndex])
            }
          }
          add(MathProblem(operands, operation = operations[problemIndex]))
        }
      }
      return mathProblems
    }

    internal data class MathProblem(val operands: List<Int>, val operation: Operation) {
      fun evaluate(): BigInteger =
        when (operation) {
          Operation.ADD ->
            operands.fold(BigInteger.ZERO) { acc, operand -> acc + operand.toBigInteger() }
          Operation.MULTIPLY ->
            operands.fold(BigInteger.ONE) { acc, operand -> acc * operand.toBigInteger() }
        }
    }
  }

  class Part2 {
    fun solve(input: String): BigInteger {
      val mathProblems = parseInput(input)
      return mathProblems.fold(BigInteger.ZERO) { sum, problem -> sum + problem.evaluate() }
    }

    private fun parseInput(input: String): List<MathProblem> {
      val lines = input.lines()

      val operandLines = lines.dropLast(1)
      val operandTextGrid: List<List<Char>> = operandLines.map { it.toList() }
      check(operandTextGrid.all { it.size == operandTextGrid[0].size })

      // Split at blank columns
      val splitColumnIndices: List<Int> =
        operandTextGrid[0].indices.filter { column ->
          val rows = operandTextGrid.indices
          rows.all { row -> operandTextGrid[row][column] == ' ' }
        }

      // Split operands in each row
      val operandStringRows: List<List<String>> =
        operandLines.map { line ->
          val operandStrings = mutableListOf<String>()
          var substringStartIndex = 0
          for (index in splitColumnIndices) {
            operandStrings += line.substring(substringStartIndex until index)
            substringStartIndex = index + 1
          }
          operandStrings += line.substring(substringStartIndex..line.lastIndex)
          operandStrings
        }
      check(operandStringRows.all { it.size == operandStringRows.first().size }) {
        "Expected all operand strings in each row to have the same size, but were: $operandStringRows"
      }

      val operations =
        lines
          .last()
          .split("""\s+""".toRegex())
          .filter { it.isNotEmpty() }
          .map { operationString -> operationString.toCharArray().single() }
          .map(Operation::fromSymbol)

      val mathProblems = buildList {
        // make lists of operand strings by column
        for (problemIndex in operations.indices) {
          val operandStrings = mutableListOf<String>()
          for (row in operandStringRows.indices) {
            operandStrings += operandStringRows[row][problemIndex]
          }

          add(MathProblem(operandStrings, operations[problemIndex]))
        }
      }

      return mathProblems
    }

    internal data class MathProblem(val operandStrings: List<String>, val operation: Operation) {
      init {
        check(operandStrings.all { it.length == operandStrings.first().length }) {
          "Expected all operand strings to have the same length, but were: ${operandStrings.map { "'$it'"}}"
        }
      }

      fun evaluate(): BigInteger {
        // Read vertical, right to left
        val operands: List<Long> = buildList {
          for (column in operandStrings.first().lastIndex downTo 0) {
            var digitString = ""
            for (row in operandStrings.indices) {
              digitString += "${operandStrings[row][column]}".trim()
            }
            add(digitString.toLong())
          }
        }
        return when (operation) {
          Operation.ADD ->
            operands.fold(BigInteger.ZERO) { acc, operand -> acc + operand.toBigInteger() }
          Operation.MULTIPLY ->
            operands.fold(BigInteger.ONE) { acc, operand -> acc * operand.toBigInteger() }
        }
      }
    }
  }
}

internal enum class Operation(val symbol: Char) {
  ADD('+'),
  MULTIPLY('*');

  companion object {
    fun fromSymbol(symbol: Char): Operation = Operation.entries.first { it.symbol == symbol }
  }
}
