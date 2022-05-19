package util

import comparisons.*
import java.util.Arrays
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

// extension functions for primitive type arrays
fun Array<Byte>  .mapAll(toFind: Array<*>) = this.mapAll(toFind, ByteComparator)
fun Array<Short> .mapAll(toFind: Array<*>) = this.mapAll(toFind, ShortComparator)
fun Array<Int>   .mapAll(toFind: Array<*>) = this.mapAll(toFind, IntComparator)
fun Array<Long>  .mapAll(toFind: Array<*>) = this.mapAll(toFind, LongComparator)
fun Array<Float> .mapAll(toFind: Array<*>) = this.mapAll(toFind, FloatComparator)
fun Array<Double>.mapAll(toFind: Array<*>) = this.mapAll(toFind, DoubleComparator)
fun Array<Char>  .mapAll(toFind: Array<*>) = this.mapAll(toFind, CharComparator)

/**
 * Maps all values to find in this array to their indexes of occurrence.
 * @param [toFind]: array of values to find
 * @param [comparator]: used to sort & find values in the array
 * @return [HashMap]: map of all values to their indexes of occurrence
 * *(an empty [ArrayList] for values which have not been found)*
 */
inline fun <reified T> Array<T>.mapAll(
    toFind: Array<*>,
    comparator: Comparator<T>
): HashMap<Any, ArrayList<Int>> {
    // maps all values to find to an empty arrayList
    val indexMap = HashMap<Any, ArrayList<Int>>(toFind.size)
    for (value in toFind) indexMap[value as Any] = arrayListOf()

    // retains only the values to find which are of the same type as the array
    // & sorts them to be able to perform binary search later on
    val ofType = toFind.retainOfType<T>()
    Arrays.parallelSort(ofType, comparator)

    // for every value in this array...
    for (index in this.indices) {
        // ...searches for the current value amongst the values to find...
        val foundIndex = Arrays.binarySearch(ofType, this[index], comparator)
        // ...and if it is found, maps its index to the value to find
        if (foundIndex >= 0) indexMap[ofType[foundIndex] as Any]?.add(index)
    }

    // returns the final map of indexes
    return indexMap
}

/**
 * Retains only elements of the same type as the generic [T]
 */
inline fun <reified T> Array<*>.retainOfType(): Array<T> {
    // initialises the arrayList
    val ofType = ArrayList<T>(this.size)

    // saves all the elements which are of the same type as T
    for (t in this) if (t is T) ofType.add(t)

    // returns the final array of values
    return ofType.toArray(arrayOf<T>())
}