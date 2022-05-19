import changeAPI.Change
import java.util.Random

fun main() {

    val rand = Random()
    val arraySize = 2_073_600
    val changeSize = 10_000
    val randomRange = 10_000

    val values = buildList { for (i in 0 until arraySize) add(rand.nextInt(randomRange)) }
    val toRemove = buildList { for (i in 0 until changeSize) add(rand.nextInt(randomRange))}

    val start = System.currentTimeMillis()
    val change =
        Change.of(values)
            .removeAll(toRemove)
            .apply()
    val stop = System.currentTimeMillis()

    println(stop - start)
}
