package changeAPI.operations

import changeAPI.changes.EvolvedChange
import changeAPI.changes.PrimitiveChange

interface ListMappings<I> {
    fun mapToByte(mappingFunction: (I) -> Byte): PrimitiveChange<Byte>
    fun mapToShort(mappingFunction: (I) -> Short): PrimitiveChange<Short>
    fun mapToInt(mappingFunction: (I) -> Int): PrimitiveChange<Int>
    fun mapToLong(mappingFunction: (I) -> Long): PrimitiveChange<Long>
    fun mapToFloat(mappingFunction: (I) -> Float): PrimitiveChange<Float>
    fun mapToDouble(mappingFunction: (I) -> Double): PrimitiveChange<Double>
    fun mapToChar(mappingFunction: (I) -> Char): PrimitiveChange<Char>
    fun mapToString(mappingFunction: (I) -> String): EvolvedChange<String>
    fun <O> mapToObj(mappingFunction: (I) -> O): EvolvedChange<O>
}