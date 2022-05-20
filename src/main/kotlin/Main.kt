import changeAPI.Change
import changeAPI.Generators

fun main() {

    val arraySize = 10
    val characters = Generators.letterGenerator[arraySize]

    val change =
        Change.of(characters)
            .setAt(
                0 to 'a',
                1 to 'b',
                2 to 'c'
            )
            .setAll(
                'a' to 'A',
                'b' to 'B',
                'c' to 'C'
            )
            .sorted()
            .unique()
            .apply()

    println(characters)
    println(change)
}
