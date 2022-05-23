import changeAPI.*
import changeAPI.changes.Change

fun main() {

    val arraySize = 10
    val characters = RandomIntGenerator(20).generate(arraySize)

    val change =
        Change.of("hello")
            .unique()
            .sorted()
            .apply()

    println(characters)
    println(change)
}
