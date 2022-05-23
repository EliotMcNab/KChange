package changeAPI.operations.delegations

import changeAPI.changes.Change
import changeAPI.changes.ListChange
import changeAPI.PrimitiveAccessors
import util.findFirst
import util.findLast
import util.mapAll

internal class PrimitiveAccessorsImpl<T>(
    list: List<T>,
    parent: Change<*>?,
    private val comparator: Comparator<T>
) : ListAccessorsImpl<T>(list, parent), PrimitiveAccessors<T> {
    override fun findAll(vararg toFind: T) =
        apply().mapAll(toFind.toList(), comparator)

    override fun findFirst(vararg toFind: T) =
        apply().findFirst(toFind.toList(), comparator)

    override fun findLast(vararg toFind: T) =
        apply().findLast(toFind.toList(), comparator)
}
