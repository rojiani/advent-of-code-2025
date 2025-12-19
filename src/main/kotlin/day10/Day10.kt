package day10

import kotlin.collections.getOrPut
import kotlin.math.roundToInt
import org.ojalgo.optimisation.ExpressionsBasedModel
import org.ojalgo.optimisation.Optimisation

typealias Button = Set<Int>

typealias Joltages = List<Int>

/** https://adventofcode.com/2025/day/10 */
class Day10 {
  class Part1 {
    fun solve(input: String): Int {
      val machineDescriptions: List<MachineDescription> = parseInput(input)

      var totalPresses = 0
      for (machine in machineDescriptions) {
        val minPresses =
          minPressesForIndicatorLights(machine.indicatorLights, machine.buttonWirings)
        totalPresses += minPresses
      }

      return totalPresses
    }

    private fun minPressesForIndicatorLights(
      indicatorLights: LightStates,
      buttons: List<Button>,
    ): Int {
      // Optimization - start with the fewest button presses and increase
      for (maxPresses in 1 until buttons.size) {
        val minPressesForLightStates = mutableMapOf<LightStates, Int>()

        val minPresses: Int? =
          minPressesForIndicatorLights(
            currentLightStates = indicatorLights,
            currentPresses = emptyList(),
            maxPresses = maxPresses,
            buttons = buttons,
            minPressesForLightStates = minPressesForLightStates,
          )

        if (minPresses != null) {
          return minPresses
        }
      }

      throw IllegalArgumentException(
        "No solution found for ${indicatorLights.lightStatesString}, $buttons"
      )
    }

    private fun minPressesForIndicatorLights(
      currentLightStates: LightStates,
      currentPresses: List<Button>,
      maxPresses: Int,
      buttons: List<Button>,
      minPressesForLightStates: MutableMap<LightStates, Int>,
    ): Int? {
      if (currentPresses.size > maxPresses) {
        return null
      }

      if (
        currentLightStates in minPressesForLightStates &&
          minPressesForLightStates.getValue(currentLightStates) <= currentPresses.size
      ) {
        return minPressesForLightStates.getValue(currentLightStates)
      }

      if (currentLightStates.isInitialState()) {
        return minPressesForLightStates.getOrPut(currentLightStates) { 0 }
      }

      // Don't repeat any button presses
      val otherButtonsToPress = buttons.filter { button -> button !in currentPresses }
      for (button in otherButtonsToPress) {
        val lightsBeforePress = currentLightStates.beforeButtonPress(button)
        val minFromPress: Int? =
          minPressesForIndicatorLights(
            currentLightStates = lightsBeforePress,
            currentPresses = currentPresses + listOf(button),
            maxPresses = maxPresses,
            buttons = buttons,
            minPressesForLightStates = minPressesForLightStates,
          )
        if (minFromPress != null) {
          minPressesForLightStates[currentLightStates] =
            if (currentLightStates in minPressesForLightStates) {
              minOf(minPressesForLightStates.getValue(currentLightStates), 1 + minFromPress)
            } else {
              1 + minFromPress
            }
        }
      }

      return minPressesForLightStates[currentLightStates]
    }
  }

  class Part2 {
    fun solve(input: String): Int {
      val machineDescriptions: List<MachineDescription> = parseInput(input)

      var totalPresses = 0
      for (machine in machineDescriptions) {
        val minPressesForMachine =
          requireNotNull(findMinPresses(machine.buttonWirings, machine.joltageRequirements)) {
            "No solution found for machine: $machine"
          }
        totalPresses += minPressesForMachine
      }

      return totalPresses
    }

    /**
     * Convert into a system of linear equations & use [ojAlgo](https://www.ojalgo.org/) to solve.
     * Credit to nbulteau: https://tinyurl.com/49myzm2u
     *
     * ```
     *    x0  x1    x2  x3    x4    x5
     *    (3) (1,3) (2) (2,3) (0,2) (0,1)
     * 0:                     x4    x5    = 3
     * 1:     x1                    x5    = 5
     * 2:           x2  x3    x4          = 4
     * 3: x0  x1        x3                = 7
     * ```
     * ```
     * x4 + x5 = 3
     * x1 + x5 = 5
     * x2 + x3 + x4 = 4
     * x0 + x1 + x3 = 7
     * ```
     */
    private fun findMinPresses(buttons: List<Button>, joltages: Joltages): Int? {
      val model = ExpressionsBasedModel()

      val variables = buttons.indices.map { i -> model.newVariable("x_$i").lower(0).integer(true) }

      // Add equality constraints
      for ((j, joltage) in joltages.withIndex()) {
        val constraint = model.newExpression("c_$j").level(joltage.toBigDecimal())
        for (buttonIndex in buttons.indices) {
          if (j in buttons[buttonIndex]) {
            constraint[variables[buttonIndex]] = 1
          }
        }
      }

      // Minimize the sum of all variables
      for (variable in variables) {
        variable.weight(1)
      }

      // Solve
      val result: Optimisation.Result = model.minimise()

      return when {
        result.state.isOptimal || result.state.isFeasible -> result.value.roundToInt()
        else -> throw IllegalArgumentException("Result for $buttons, $joltages: $result")
      }
    }
  }
}

private fun parseInput(input: String): List<MachineDescription> {
  val lines = input.lines()

  val machineDescriptions = buildList {
    for (line in lines) {
      val indicatorLightsString = line.substringAfter('[').substringBefore(']')
      val indicatorLights = LightStates.fromString(indicatorLightsString)

      // text within ()
      val buttonWiringStrings: List<String> =
        BUTTON_WIRING_REGEX.findAll(line).map { it.groupValues[1] }.toList()

      val buttonWirings: List<Button> =
        buttonWiringStrings.map { wiringString ->
          wiringString.split(',').map { it.trim().toInt() }.toSet()
        }

      val joltages: Joltages =
        requireNotNull(JOLTAGE_REGEX.find(line)?.groupValues[1]?.split(',')?.map { it.toInt() })

      add(MachineDescription(indicatorLights, buttonWirings, joltages))
    }
  }
  return machineDescriptions
}

data class MachineDescription(
  val indicatorLights: LightStates,
  val buttonWirings: List<Button>,
  val joltageRequirements: Joltages,
)

data class LightStates(val states: List<Boolean>) {

  val lightStatesString: String = states.map { if (it) ON else OFF }.joinToString("")

  fun beforeButtonPress(button: Button): LightStates {
    val newStates = states.toMutableList()
    for (buttonIndex in button) {
      newStates[buttonIndex] = !states[buttonIndex]
    }
    return LightStates(newStates)
  }

  fun isInitialState(): Boolean = states.all { !it }

  override fun toString(): String = lightStatesString

  companion object {
    // ".###.#"
    fun fromString(lightStatesString: String): LightStates {
      return LightStates(states = lightStatesString.map { it == ON })
    }
  }
}

const val ON = '#'
const val OFF = '.'

// Text within ( )
val BUTTON_WIRING_REGEX = Regex("""\(([^)]*)\)""")
// Text within { }
val JOLTAGE_REGEX = Regex("""\{([^)]*)}""")
