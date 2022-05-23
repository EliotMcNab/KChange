package changeAPI.operations

import changeAPI.changes.EvolvedChange
import changeAPI.changes.PrimitiveChange

interface ListMappings<I> {
    fun mapToInt(mappingFunction: (I) -> Int): PrimitiveChange<Int>
    fun <O> mapToObj(mappingFunction: (I) -> O): EvolvedChange<O>
}