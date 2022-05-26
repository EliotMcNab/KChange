package changeAPI.changes

import changeAPI.*
import changeAPI.information.*
import changeAPI.operations.ListTransforms
import comparisons.*
sealed class TransformImpl<T>(
    list: List<T>,
    parent: Change<*>?,
) : ListChange<T>(list, parent), ListTransforms<T> {
    override fun mapToByte(mappingFunction: (T) -> Byte): PrimitiveChange<Byte> =
        PrimitiveAdapter(ByteComparator, ByteOperator, this, SimpleMapping(mappingFunction, this))

    override fun mapToShort(mappingFunction: (T) -> Short): PrimitiveChange<Short> =
        PrimitiveAdapter(ShortComparator, ShortOperator, this, SimpleMapping(mappingFunction, this))

    override fun mapToInt(mappingFunction: (T) -> Int): PrimitiveChange<Int> =
        PrimitiveAdapter(IntComparator, IntOperator, this, SimpleMapping(mappingFunction, this))

    override fun mapToLong(mappingFunction: (T) -> Long): PrimitiveChange<Long> =
        PrimitiveAdapter(LongComparator, LongOperator, this, SimpleMapping(mappingFunction, this))

    override fun mapToFloat(mappingFunction: (T) -> Float): PrimitiveChange<Float> =
        PrimitiveAdapter(FloatComparator, FloatOperator, this, SimpleMapping(mappingFunction, this))

    override fun mapToDouble(mappingFunction: (T) -> Double): PrimitiveChange<Double> =
        PrimitiveAdapter(DoubleComparator, DoubleOperator, this, SimpleMapping(mappingFunction, this))

    override fun mapToChar(mappingFunction: (T) -> Char): PrimitiveChange<Char> =
        PrimitiveAdapter(CharComparator, CharOperator, this, SimpleMapping(mappingFunction, this))

    override fun mapToString(mappingFunction: (T) -> String): EvolvedChange<String> =
        SimpleMapping(mappingFunction, this)

    override fun <O> mapToObj(mappingFunction: (T) -> O): EvolvedChange<O> =
        SimpleMapping(mappingFunction, this)

    // GROUPING
    override fun group(groupSize: Int): EvolvedChange<List<T>> =
        Group(groupSize, this)

    override fun groupBy(vararg filters: (T) -> Boolean): EvolvedChange<List<T>> =
        GroupBy(filters.toList(), this)
}

