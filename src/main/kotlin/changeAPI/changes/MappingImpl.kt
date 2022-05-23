package changeAPI.changes

import changeAPI.PrimitiveAdapter
import changeAPI.SimpleMapping
import changeAPI.information.IntOperator
import changeAPI.operations.ListMappings
import comparisons.IntComparator

sealed class MappingImpl<T>(
    list: List<T>,
    parent: Change<*>?,
) : ListChange<T>(list, parent), ListMappings<T> {
    override fun mapToInt(mappingFunction: (T) -> Int): PrimitiveChange<Int> =
        PrimitiveAdapter(IntComparator, IntOperator, this, SimpleMapping(mappingFunction, this))

    override fun <O> mapToObj(mappingFunction: (T) -> O): EvolvedChange<O> =
        SimpleMapping(mappingFunction, this)
}