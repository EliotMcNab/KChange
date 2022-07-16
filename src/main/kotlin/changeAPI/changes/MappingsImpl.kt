package changeAPI.changes

import changeAPI.*
import changeAPI.information.*
import changeAPI.operations.ListMappings
import comparisons.*

/**
 * Implements mappings from one [Change] type to another
 * @author Eliot McNab
 */
sealed class MappingsImpl<T>(
    list: List<T>,
    parent: Change<*>?,
) : Change<T>(list, parent), ListMappings<T> {

    /**
     * Maps the current [Change] to a Change<[Byte]>, following the instructions passed by the given [mappingFunction]
     * @author Eliot McNab
     */
    override fun mapToByte(mappingFunction: (T) -> Byte): PrimitiveChange<Byte> =
        PrimitiveAdapter(ByteComparator, ByteOperator, this, SimpleMapping(mappingFunction, this))

    /**
     * Maps the current [Change] to a Change<[Short]>, following the instructions passed by the given [mappingFunction]
     * @author Eliot McNab
     */
    override fun mapToShort(mappingFunction: (T) -> Short): PrimitiveChange<Short> =
        PrimitiveAdapter(ShortComparator, ShortOperator, this, SimpleMapping(mappingFunction, this))

    /**
     * Maps the current [Change] to a Change<[Int]>, following the instructions passed by the given [mappingFunction]
     * @author Eliot McNab
     */
    override fun mapToInt(mappingFunction: (T) -> Int): PrimitiveChange<Int> =
        PrimitiveAdapter(IntComparator, IntOperator, this, SimpleMapping(mappingFunction, this))

    /**
     * Maps the current [Change] to a Change<[Long]>, following the instructions passed by the given [mappingFunction]
     * @author Eliot McNab
     */
    override fun mapToLong(mappingFunction: (T) -> Long): PrimitiveChange<Long> =
        PrimitiveAdapter(LongComparator, LongOperator, this, SimpleMapping(mappingFunction, this))

    /**
     * Maps the current [Change] to a Change<[Float]>, following the instructions passed by the given [mappingFunction]
     * @author Eliot McNab
     */
    override fun mapToFloat(mappingFunction: (T) -> Float): PrimitiveChange<Float> =
        PrimitiveAdapter(FloatComparator, FloatOperator, this, SimpleMapping(mappingFunction, this))

    /**
     * Maps the current [Change] to a Change<[Double]>, following the instructions passed by the given [mappingFunction]
     * @author Eliot McNab
     */
    override fun mapToDouble(mappingFunction: (T) -> Double): PrimitiveChange<Double> =
        PrimitiveAdapter(DoubleComparator, DoubleOperator, this, SimpleMapping(mappingFunction, this))

    /**
     * Maps the current [Change] to a Change<[Char]>, following the instructions passed by the given [mappingFunction]
     * @author Eliot McNab
     */
    override fun mapToChar(mappingFunction: (T) -> Char): PrimitiveChange<Char> =
        PrimitiveAdapter(CharComparator, CharOperator, this, SimpleMapping(mappingFunction, this))

    /**
     * Maps the current [Change] to a Change<[String]>, following the instructions passed by the given [mappingFunction]
     * @author Eliot McNab
     */
    override fun mapToString(mappingFunction: (T) -> String): EvolvedChange<String> =
        SimpleMapping(mappingFunction, this)

    /**
     * Maps the current [Change] to a Change<[O]>, following the instructions passed by the given [mappingFunction].
     * **This should not be used to map to primitive types as the function returns an [EvolvedChange] which does not
     * have access to primitive functions**
     * @author Eliot McNab
     */
    override fun <O> mapToObj(mappingFunction: (T) -> O): EvolvedChange<O> =
        SimpleMapping(mappingFunction, this)

    // GROUPING

    /**
     * Groups the elements in a [Change] into [List]s of the given [groupSize]
     * @author Eliot McNab
     */
    override fun group(groupSize: Int): EvolvedChange<List<T>> =
        Group(groupSize, this)

    /**
     * Groups the elements in a [Change] into [Lists][List] of elements that match the given [filters]. Groups are
     * resolved in the order in which they are passed, so if for example an element satisfies more than 1 filter, _it
     * will be grouped into the first filter by default_.
     *
     * ### _Example_
     * ```
     * val change = Change
     *      .of(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
     *      .groupBy(::even, ::odd)
     *      .apply()
     *
     * println(change)
     *
     * . . .
     *
     * fun even(number: Int) = number % 2 == 0 // true if a number is even
     * fun odd(number: Int) = !even(number)    // true if a number is odd
     * ```
     * ### _Output_
     * ```
     * >> [[2, 4, 6, 8, 10], [1, 3, 5, 7, 9]]
     * ```
     */
    override fun groupBy(vararg filters: (T) -> Boolean): EvolvedChange<List<T>> =
        GroupBy(filters.toList(), this)
}

