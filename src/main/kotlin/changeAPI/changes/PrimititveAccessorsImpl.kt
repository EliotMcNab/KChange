package changeAPI.changes

import changeAPI.EvolvedAccessors
import changeAPI.PrimitiveAccessors
import changeAPI.information.Operator
import util.*
import java.util.function.Predicate

sealed class PrimitiveAccessorsImpl<T>(
    list: List<T>,
    parent: Change<*>?,
    private val comparator: Comparator<T>,
    operator: Operator<T>,
) : PrimitiveCollectingImpl<T>(list, parent, operator),
    PrimitiveAccessors<T>,
    EvolvedAccessors<T> {

    override fun findAll(vararg toFind: T) =
        apply().mapAll(toFind.toList(), comparator)

    override fun findFirst(vararg toFind: T) =
        apply().findFirst(toFind.toList(), comparator)

    override fun findLast(vararg toFind: T) =
        apply().findLast(toFind.toList(), comparator)

    // FINDING
    override fun findAll(comparator: Comparator<T>, vararg toFind: T) =
        apply().mapAll(toFind.toList(), comparator)

    override fun findFirst(comparator: Comparator<T>, vararg toFind: T) =
        apply().findFirst(toFind.toList(), comparator)

    override fun findLast(comparator: Comparator<T>, vararg toFind: T) =
        apply().findLast(toFind.toList(), comparator)

    override fun findAllMatches(filter: (T) -> Boolean) =
        apply().findAllMatches(filter)

    override fun findFirstMatch(filter: Predicate<T>) =
        apply().findFirstMatch(filter)

    override fun findLastMatch(filter: Predicate<T>) =
        apply().findLastMatch(filter)

    // GETTING
    override fun get(index: Int) = apply()[index]
    override fun getAt(vararg indexes: Int) = apply().getAt(indexes.toList())
    override fun getAllMatches(filter: (T) -> Boolean) = apply().getAt(findAllMatches(filter))
    override fun getFirstMatch(filter: Predicate<T>) = apply()[findFirstMatch(filter)]
    override fun getLastMatch(filter: Predicate<T>) = apply()[findLastMatch(filter)]
}