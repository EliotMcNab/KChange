import changeAPI.Change
import java.util.Random

fun main() {

    val rand = Random()
    val arraySize = 50
    val changeSize = 10
    val randomRange = 10

    val values = buildList { for (i in 0 until arraySize) add(rand.nextInt(randomRange)) }
    val toRemove = buildList { for (i in 0 until changeSize) add(rand.nextInt(randomRange))}

    val start = System.currentTimeMillis()
    val change =
        Change.of(values)
            .removeFirst(toRemove)
            .rollbackTo(5)
            .apply()
    val stop = System.currentTimeMillis()

    println(stop - start)
}
