import changeAPI.*
import changeAPI.changes.Change
import comparisons.DoubleComparator
import geometry.Point
import geometry.minus
import geometry.plus
import util.addBy
import util.operateBy
import util.subBy

fun main() {

    val arraySize = 10
    val values = RandomDoubleGenerator(max = 20.0, precision = 2).generate(arraySize)

    val operator = operateBy<Point>(
        addBy { point1, point2 -> point1 + point2 },
        subBy { point1, point2 -> point1 - point2 }
    )

    val changes =
        Change.of(values)
            .group(2)
            .forEach { list -> list.sortedWith(DoubleComparator) }
            .mapToObj { coordinates -> Point(coordinates[0], coordinates[1]) }
            .subAll(subBy { point1, point2 -> point1 - point2 })

    println(changes)
}
