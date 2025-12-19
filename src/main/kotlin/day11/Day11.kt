package day11

class Day11 {
  class Part1 {
    fun solve(input: String): Long {
      val connections: Map<Device, List<Device>> = parseInput(input)
      val allDevices: Set<Device> = (connections.keys + connections.values.flatten()).toSet()

      val start: Device = allDevices.single { it.name == "you" }
      val target: Device = allDevices.single { it.name == "out" }

      return getNumberOfPaths(connections, current = start, target = target, cache = mutableMapOf())
    }
  }

  class Part2 {
    fun solve(input: String): Long {
      val connections: Map<Device, List<Device>> = parseInput(input)
      val allDevices: Set<Device> = (connections.keys + connections.values.flatten()).toSet()

      val svr = allDevices.single { it.name == "svr" }
      val out = allDevices.single { it.name == "out" }
      val dac = allDevices.single { it.name == "dac" }
      val fft = allDevices.single { it.name == "fft" }

      // 1. Find all paths with svr -> dac -> fft -> out
      val pathsWithDacFirst =
        getPathsThroughMiddleNodes(
          connections,
          svr = svr,
          firstMiddleNode = dac,
          secondMiddleNode = fft,
          out = out,
        )

      // 2. Find all paths with svr -> fft -> dac -> out
      val pathsWithFftFirst =
        getPathsThroughMiddleNodes(
          connections,
          svr = svr,
          firstMiddleNode = fft,
          secondMiddleNode = dac,
          out = out,
        )

      return pathsWithDacFirst + pathsWithFftFirst
    }

    private fun getPathsThroughMiddleNodes(
      connections: Map<Device, List<Device>>,
      svr: Device,
      firstMiddleNode: Device,
      secondMiddleNode: Device,
      out: Device,
    ): Long {
      val middleNodePaths =
        getNumberOfPathsBetween(connections, start = firstMiddleNode, target = secondMiddleNode)
      if (middleNodePaths == 0L) {
        return 0L
      }

      val secondMiddleNodeToOutPaths =
        getNumberOfPathsBetween(connections, start = secondMiddleNode, target = out)
      if (secondMiddleNodeToOutPaths == 0L) {
        return 0L
      }

      val startToFirstMiddleNodePaths =
        getNumberOfPathsBetween(connections, start = svr, target = firstMiddleNode)
      if (startToFirstMiddleNodePaths == 0L) {
        return 0L
      }

      return startToFirstMiddleNodePaths * middleNodePaths * secondMiddleNodeToOutPaths
    }

    private fun getNumberOfPathsBetween(
      connections: Map<Device, List<Device>>,
      start: Device,
      target: Device,
    ): Long {
      val cache = mutableMapOf<Device, Long>()
      return getNumberOfPaths(connections, current = start, target = target, cache = cache)
    }
  }
}

private fun parseInput(input: String): Map<Device, List<Device>> =
  input
    .lines()
    .map { it.trim() }
    .associate { line ->
      val sourceDevice = Device(line.substringBefore(':'))
      val outputDevices =
        line
          .substringAfter(':')
          .split(' ')
          .filter { it.isNotEmpty() }
          .map { Device(name = it.trim()) }
      sourceDevice to outputDevices
    }

data class Device(val name: String) {
  init {
    check(name.matches("[a-z]{3}".toRegex())) { "Invalid device name: '$name" }
  }
}

private fun getNumberOfPaths(
  connections: Map<Device, List<Device>>,
  current: Device,
  target: Device,
  cache: MutableMap<Device, Long>,
): Long =
  when (current) {
    in cache -> cache.getValue(current)
    target -> cache.getOrPut(current) { 1L }
    else -> {
      val successors = connections[current] ?: emptyList()

      var pathsFromCurrentDevice = 0L
      for (successor in successors) {
        pathsFromCurrentDevice += getNumberOfPaths(connections, current = successor, target, cache)
      }
      cache[current] = pathsFromCurrentDevice
      pathsFromCurrentDevice
    }
  }
