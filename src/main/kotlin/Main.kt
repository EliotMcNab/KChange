import changeAPI.Change
import java.util.Random

fun main() {
    val rand = Random()
    val arraySize = 10

    val characters = buildList { for (index in 0 until arraySize) add(rand.nextInt(97, 122).toChar()) }

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
