package changeAPI.operations

import changeAPI.changes.EvolvedChange
import changeAPI.changes.PrimitiveChange

interface ListTransforms<I> {
    // mapping
    fun mapToByte(mappingFunction: (I) -> Byte): PrimitiveChange<Byte>
    fun mapToShort(mappingFunction: (I) -> Short): PrimitiveChange<Short>
    fun mapToInt(mappingFunction: (I) -> Int): PrimitiveChange<Int>
    fun mapToLong(mappingFunction: (I) -> Long): PrimitiveChange<Long>
    fun mapToFloat(mappingFunction: (I) -> Float): PrimitiveChange<Float>
    fun mapToDouble(mappingFunction: (I) -> Double): PrimitiveChange<Double>
    fun mapToChar(mappingFunction: (I) -> Char): PrimitiveChange<Char>
    fun mapToString(mappingFunction: (I) -> String): EvolvedChange<String>
    fun <O> mapToObj(mappingFunction: (I) -> O): EvolvedChange<O>

    // grouping
    fun group(groupSize: Int): EvolvedChange<List<I>>
    fun groupBy(vararg filters: (I) -> Boolean): EvolvedChange<List<I>>
}