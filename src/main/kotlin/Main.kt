import changeAPI.*
import changeAPI.changes.Change
import comparisons.DoubleComparator
import geometry.Point
import geometry.plus
import util.sumBy

fun main() {

    val arraySize = 10
    val values = RandomDoubleGenerator(max = 20.0, precision = 2).generate(arraySize)

    val changes =
        Change.of(values)
            .group(2)
            .forEach { list -> list.sortedWith(DoubleComparator) }
            .mapToObj { coordinates -> Point(coordinates[0], coordinates[1]) }
            .sumOf(sumBy { a, b -> a + b })

    println(changes)
}
