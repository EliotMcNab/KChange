import changeAPI.*
import changeAPI.changes.Change
import geometry.Point

fun main() {

    val arraySize = 10
    val characters = RandomIntGenerator(20).generate(arraySize)

    val change =
        Change.of("hello")
            .add('a')
            .unique()
            .sorted()
            .mapToInt { c: Char -> c.code }
            .mapToObj { i: Int -> Point(i.toDouble(), i.toDouble()) }
            .mapToInt { point: Point ->  point.distance().toInt() }
            .apply()

    println(characters)
    println(change)
}
