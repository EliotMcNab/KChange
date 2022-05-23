import changeAPI.*
import comparisons.IntComparator

fun main() {

    val arraySize = 10
    val characters = RandomIntGenerator(20).generate(arraySize)

    val change =
        Change.of(characters)
            .setFirst(
                IntComparator,
                12 to 1
            )
            .setAt(
                0 to 1,
                2 to 3
            )
            .apply()

    println(characters)
    println(change)
}
