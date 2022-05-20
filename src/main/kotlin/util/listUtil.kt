package util

import comparisons.*
import java.util.function.Predicate
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun <T> List<T>.wrapIndexes(): List<List<Any?>> {
    val list = this
    return buildList { for (index in list.indices) add(listOf(list[index], index)) }
}

@JvmName("mapAllByte")
fun List<Byte>  .mapAll(toFind: List<Byte>)   = mapAll(toFind, ByteComparator)
@JvmName("mapAllShort")
fun List<Short> .mapAll(toFind: List<Short>)  = mapAll(toFind, ShortComparator)
@JvmName("mapAllInt")
fun List<Int>   .mapAll(toFind: List<Int>)    = mapAll(toFind, IntComparator)
@JvmName("mapAllLong")
fun List<Long>  .mapAll(toFind: List<Long>)   = mapAll(toFind, LongComparator)
@JvmName("mapAllFloat")
fun List<Float> .mapAll(toFind: List<Float>)  = mapAll(toFind, FloatComparator)
@JvmName("mapAllDouble")
fun List<Double>.mapAll(toFind: List<Double>) = mapAll(toFind, DoubleComparator)
@JvmName("mapAllChar")
fun List<Char>  .mapAll(toFind: List<Char>)   = mapAll(toFind, CharComparator)

fun <T> List<T>.mapAll(
    toFind: List<T>,
    comparator: Comparator<T>
): Map<T, List<Int>> {
    val indexMap = HashMap<T, ArrayList<Int>>(toFind.size)

    val sortedToFind = toFind.sortedWith(comparator)

    var i = 0
    for (index in indices) {
        val foundIndex = sortedToFind.binarySearch(get(index), comparator)
        if (foundIndex >= 0) {
            val key = sortedToFind[foundIndex]
            if (key !in indexMap) {
                i++
                indexMap[key] = arrayListOf(index)
            } else indexMap[key]?.add(index)
        }
    }

    return indexMap
}

@JvmName("findAllByte")
fun List<Byte>.findAll(toFind: List<Byte>) = findAll(toFind, ByteComparator)
@JvmName("findAllShort")
fun List<Short>.findAll(toFind: List<Short>) = findAll(toFind, ShortComparator)
@JvmName("findAllInt")
fun List<Int>.findAll(toFind: List<Int>) = findAll(toFind, IntComparator)
@JvmName("findAllLong")
fun List<Long>.findAll(toFind: List<Long>) = findAll(toFind, LongComparator)
@JvmName("findAllFloat")
fun List<Float>.findAll(toFind: List<Float>) = findAll(toFind, FloatComparator)
@JvmName("findAllDouble")
fun List<Double>.findAll(toFind: List<Double>) = findAll(toFind, DoubleComparator)
@JvmName("findAllChar")
fun List<Char>.findAll(toFind: List<Char>) = findAll(toFind, CharComparator)

fun <T> List<T>.findAll(
    toFind: List<T>,
    comparator: Comparator<T>
): List<Int> {
    val occurrences = arrayListOf<Int>()
    val sortedToFind = toFind.sortedWith(comparator)

    for (index in indices) if (sortedToFind.binarySearch(get(index), comparator) >= 0) occurrences.add(index)

    return occurrences
}

@JvmName("findFirstByte")
fun List<Byte>.findFirst(toFind: List<Byte>) = findFirst(toFind, ByteComparator)
@JvmName("findFirstShort")
fun List<Short>.findFirst(toFind: List<Short>) = findFirst(toFind, ShortComparator)
@JvmName("findFirstInt")
fun List<Int>.findFirst(toFind: List<Int>) = findFirst(toFind, IntComparator)
@JvmName("findFirstLong")
fun List<Long>.findFirst(toFind: List<Long>) = findFirst(toFind, LongComparator)
@JvmName("findFirstFloat")
fun List<Float>.findFirst(toFind: List<Float>) = findFirst(toFind, FloatComparator)
@JvmName("findFirstDouble")
fun List<Double>.findFirst(toFind: List<Double>) = findFirst(toFind, DoubleComparator)
@JvmName("findFirstChar")
fun List<Char>.findFirst(toFind: List<Char>) = findFirst(toFind, CharComparator)

fun <T> List<T>.findFirst(
    toFind: List<T>,
    comparator: Comparator<T>
) = findFirstOrLast(this, toFind, comparator, false)

fun <T> List<T>.findLast(
    toFind: List<T>,
    comparator: Comparator<T>
) = findFirstOrLast(this, toFind, comparator, true)

@JvmName("findLastByte")
fun List<Byte>.findLast(toFind: List<Byte>) = findLast(toFind, ByteComparator)
@JvmName("findLastShort")
fun List<Short>.findLast(toFind: List<Short>) = findLast(toFind, ShortComparator)
@JvmName("findLastInt")
fun List<Int>.findLast(toFind: List<Int>) = findLast(toFind, IntComparator)
@JvmName("findLastLong")
fun List<Long>.findLast(toFind: List<Long>) = findLast(toFind, LongComparator)
@JvmName("findLastFloat")
fun List<Float>.findLast(toFind: List<Float>) = findLast(toFind, FloatComparator)
@JvmName("findLastDouble")
fun List<Double>.findLast(toFind: List<Double>) = findLast(toFind, DoubleComparator)
@JvmName("findLastChar")
fun List<Char>.findLast(toFind: List<Char>) = findLast(toFind, CharComparator)

private fun <T> findFirstOrLast(
    list: List<T>,
    toFind: List<T>,
    comparator: Comparator<T>,
    findLast: Boolean
): List<Int> {
    val comparator = ListComparator(comparator, 0) as Comparator<List<Any?>>
    val resultIndexes = buildList(toFind.size) {for (index in toFind.indices) add(-1)}.toMutableList()
    val wrappedFind = toFind.wrapIndexes().sortedWith(comparator)

    for (index in list.indices) {
        val foundIndex = wrappedFind.binarySearch(listOf(list[index]), comparator)
        if (foundIndex >= 0) {
            val indexInResult = wrappedFind[foundIndex][1] as Int
            val isValidIndex = resultIndexes[indexInResult] < 0 || index < resultIndexes[indexInResult] != findLast

            if (isValidIndex) resultIndexes[wrappedFind[foundIndex][1] as Int] = index
        }
    }

    return resultIndexes
}

fun <T> List<T>.findMatches(
    filter: Predicate<T>
): List<Int> {
    val occurrences = arrayListOf<Int>()
    for (index in indices) if (filter.test(get(index))) occurrences.add(index)
    return occurrences
}

fun <T> List<T>.removeAt(
    indexes: List<Int>
): List<T> {
    val remaining = arrayListOf<T>()

    var lastIndex = 0
    for (index in indexes.sorted()) {
        if (index < 0) continue
        if (index >= size) throw IndexOutOfBoundsException("No element to remove at index $index in list of size $size")

        remaining.addAll(subList(lastIndex, index))
        lastIndex = index + 1
    }

    remaining.addAll(subList(lastIndex, size))

    return remaining
}

fun <T> List<T>.retainAt(
    indexes: List<Int>
): List<T> {
    val remaining = arrayListOf<T>()

    for (index in indexes) if (index < 0) continue else remaining.add(get(index))

    return remaining
}

inline fun <reified T> List<*>.retainOfType(): List<T> {
    val ofType = ArrayList<T>()

    for (value in this) if (value is T) ofType.add(value)

    return ofType
}

fun <T> List<T>.setAll(
    replacingMap: Map<T, T>,
    comparator: Comparator<T>
): List<T> {
    val replaced = toMutableList()
    val indexMap = mapAll(replacingMap.keys.toList(), comparator)

    for (key in indexMap.keys) replaced.setAt(indexMap[key]!!, replacingMap[key]!!)

    return replaced
}

fun <T> List<T>.setFirst(
    replacingMap: Map<T, T>,
    comparator: Comparator<T>
): List<T> {
    val replaced = toMutableList()
    val keys = replacingMap.keys.toList()
    val found = findFirst(keys, comparator)

    for (index in found.indices) if (found[index] >= 0) replaced[found[index]] = replacingMap[keys[index]]!!

    return replaced
}

fun <T> List<T>.setLast(
    replacingMap: Map<T, T>,
    comparator: Comparator<T>
): List<T> {
    val replaced = toMutableList()
    val keys = replacingMap.keys.toList()
    val found = findLast(keys, comparator)

    for (index in found.indices) if (found[index] >= 0) replaced[found[index]] = replacingMap[keys[index]]!!

    return replaced
}

fun <T> MutableList<T>.setAt(
    indexes: List<Int>,
    value: T
) {
    for (index in indexes) set(index, value)
}
