import changeAPI.RandomIntGenerator
import changeAPI.changes.Change
import comparisons.IntComparator

fun main() {

    // region REMOVAL TEST
    /*
    val arraySize = 2_073_600
    val changeSize = 10_000
    val valueRange = 255 * 255 * 255

    val values = RandomIntGenerator(valueRange).generate(arraySize).toMutableList()
    val toRemove = RandomIntGenerator(valueRange).generate(changeSize)

    val changeStart = System.currentTimeMillis()
    val changeResult = Change.of(values).removeAll(toRemove).apply()
    val changeStop = System.currentTimeMillis()

    val listStart = System.currentTimeMillis()
    values.removeAll(toRemove)
    val listStop = System.currentTimeMillis()

    val changeTime = changeStop - changeStart
    val listTime = listStop - listStart

    var removalError = changeResult.size != values.size


    if (!removalError) {
        for (index in values.indices) {
            if (values[index] != changeResult[index]) {
                removalError = true
                break
            }
        }

    }

    println("removal errors: $removalError")
    println("change duration: ${changeTime}ms")
    println("list duration: ${listTime}ms")
    */
    // endregion

    val valueRange = 20
    val size = 20
    val values = RandomIntGenerator(valueRange).generate(size)

    val change = Change
        .of(values)
        .groupBy(::even, ::odd)
        .apply()

    println(change)
}

fun even(number: Int) = number % 2 == 0

fun odd(number: Int) = !even(number)
