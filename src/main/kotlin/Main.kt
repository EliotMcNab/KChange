import changeAPI.*
import changeAPI.changes.Change
import comparisons.DoubleComparator
import geometry.Point

fun main() {

    val arraySize = 9
    val values = RandomDoubleGenerator(max = 20.0, precision = 2).generate(arraySize)

    val change =
        Change.of(values)
            .groupBy({ num -> num in 0.0..10.0 }, { num -> num in 10.0..20.0 })
            .forEach { list: List<Double> -> list.sortedWith(DoubleComparator) }
            .apply()
            .sortedWith(compareBy { it[0] })

    println(values)
    println(change)
}
