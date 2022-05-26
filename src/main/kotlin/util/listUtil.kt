package util

import changeAPI.Generator
import changeAPI.information.Operator
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
    val listComparator = ListComparator(comparator, 0) as Comparator<List<Any?>>
    val resultIndexes = buildList(toFind.size) {for (index in toFind.indices) add(-1)}.toMutableList()
    val wrappedFind = toFind.wrapIndexes().sortedWith(listComparator)

    for (index in list.indices) {
        val foundIndex = wrappedFind.binarySearch(listOf(list[index]), listComparator)
        if (foundIndex >= 0) {
            val indexInResult = wrappedFind[foundIndex][1] as Int
            val isValidIndex = resultIndexes[indexInResult] < 0 || index < resultIndexes[indexInResult] != findLast

            if (isValidIndex) resultIndexes[wrappedFind[foundIndex][1] as Int] = index
        }
    }

    return resultIndexes
}

fun <T> List<T>.findAllMatches(
    filter: Predicate<T>
): List<Int> {
    val occurrences = arrayListOf<Int>()
    for (index in indices) if (filter.test(get(index))) occurrences.add(index)
    return occurrences
}

fun <T> List<T>.findFirstMatch(
    filter: Predicate<T>
): Int {
    for (index in indices) if (filter.test(get(index))) return index
    return -1
}

fun <T> List<T>.findLastMatch(
    filter: Predicate<T>
): Int {
    for (index in indices.reversed()) if (filter.test(get(index))) return index
    return -1
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

fun <T> List<T>.setAt(
    indexMap: Map<Int, T>
): List<T> {
    val replaced = toMutableList()
    val indexes = indexMap.keys
    val errorMessage = {index: Int -> "Cannot set value at index $index in list of size $size"}

    for (index in indexes) require(index in indices) {errorMessage(index)}.also { replaced[index] = indexMap[index]!! }

    return replaced
}

fun <T> MutableList<T>.setAt(
    indexes: List<Int>,
    value: T
) {
    for (index in indexes) set(index, value)
}

fun <T> List<T>.getAt(
    indexes: List<Int>
): List<T> = indexes.iterator().run { Generator { get(next()) }.generate(size) }

operator fun <T> List<T>.get(from: Int, to: Int): List<T> = subList(from, to)

fun <T> List<T>.sumOf(
    operator: Operator<T>
): T {
    var sum = get(0)
    
    for (index in 1 until size) sum = operator.add(sum, get(index))
    return sum
}

fun <T> List<T>.differenceOf(
    operator: Operator<T>
): T {
    var diff = get(0)

    for (index in 1 until size) diff = operator.sub(diff, get(index))
    return diff
}

fun <T> List<T>.productOf(
    operator: Operator<T>
): T {
    var prod = get(0)

    for (index in 1 until size) prod = operator.mult(prod, get(index))
    return prod
}

fun <T> List<T>.quotientOf(
    operator: Operator<T>
): T {
    var quotient = get(0)

    for (index in 1 until size) quotient = operator.div(quotient, get(index))
    return quotient
}

fun <T> List<T>.group(
    groupSize: Int,
): List<List<T>> {
    val result = ArrayList<ArrayList<T>>(size / groupSize)

    for (index in indices) {
        if (index % groupSize == 0) result.add(arrayListOf(get(index)))
        else                        result.last().add(get(index))
    }

    return result
}

@JvmName("groupByRanges")
fun <T : Comparable<T>> List<T>.groupBy(
    ranges: List<ClosedRange<T>>,
): List<List<T>> {
    val result = ArrayList<ArrayList<T>>(ranges.size)

    for (value in this) for (index in ranges.indices) if (value in ranges[index]) result[index].add(value)

    return result
}

@JvmName("groupByFilters")
fun <T> List<T>.groupBy(
    filters: List<(T) -> Boolean>,
): List<List<T>> {
    val result = buildList<ArrayList<T>> { for (index in filters.indices) add(arrayListOf()) }

    for (value in this) for (index in filters.indices) if (filters[index](value)) result[index].add(value)

    return result
}
