package aoc2025

private const val OUT_NAME = "out"

fun main() {
//    val input = readInput("day11_test")
    val input = readInput("day11")
    part1(input)
    part2(input)
}

private fun part1(input: List<String>) {
    val deviceMap: MutableMap<String, Device> = mutableMapOf()
    input.forEach { line ->
        val name = line.substringBefore(':')
        val device = getFromMapOrCreateAndPut(name, deviceMap)
        val children = line.substringAfter(':')
            .trim()
            .split(' ')
            .map { getFromMapOrCreateAndPut(it, deviceMap) }
        device.children = children
    }
    val from = deviceMap["you"]!!
    val to = deviceMap["out"]!!
    println(calculateAmountOfPaths(from, to))
}

private fun part2(input: List<String>) {
    val deviceMap: MutableMap<String, List<String>> = mutableMapOf()
    input.forEach { line ->
        val name = line.substringBefore(':')
        val children = line.substringAfter(':')
            .trim()
            .split(' ')
        deviceMap[name] = children
    }
    val svrFft = deviceMap.countPaths("svr", "fft")
    val fftDac = deviceMap.countPaths("fft", "dac")
    val dacOut = deviceMap.countPaths("dac", "out")
    val result = svrFft * fftDac * dacOut
    println(result)
}

private fun Map<String, List<String>>.countPaths(
    from: String,
    dst: String,
    cache: MutableMap<String, Long> = mutableMapOf()
): Long {
    return cache.getOrPut(from) {
        if (from == dst) {
            1
        } else {
            get(from).orEmpty().sumOf { child ->
                countPaths(child, dst, cache)
            }
        }
    }

}

private fun calculateAmountOfPaths(from: Device, to: Device): Int {
    val allPaths = mutableListOf<Set<String>>()
    findAllPaths(from, to, mutableSetOf(), allPaths)
    return allPaths.size
}

private fun getFromMapOrCreateAndPut(
    name: String,
    map: MutableMap<String, Device>
): Device {
    val newDevice = name.toDevice()
    return map.putIfAbsent(name, newDevice) ?: newDevice
}

fun String.toDevice(): Device {
    return Device(name = this)
}

class Device(
    val name: String,
    var children: List<Device>? = null
)

fun findAllPaths(
    device: Device,
    finalDevice: Device,
    path: MutableSet<String>,
    allPaths: MutableList<Set<String>>
) {
    path.add(device.name)
    if (device.name == finalDevice.name) {
        allPaths.add(path.toSet())
        path.remove(device.name)
        return
    }
    for (child in device.children ?: emptyList()) {
        if (!path.contains(child.name)) {
            findAllPaths(child, finalDevice,path, allPaths)
        }
    }
    path.remove(device.name)
}










