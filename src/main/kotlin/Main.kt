import changeAPI.*
import changeAPI.changes.Change

fun main() {

    val arraySize = 10
    val characters = RandomIntGenerator(20).generate(arraySize)

    val change =
        Change.of(characters)
            .unique()
            .sorted()

    println(characters)
    println(change.apply())
}
