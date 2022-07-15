package changeAPI.changes

import changeAPI.EvolvedAccessors
import util.*
import java.util.function.Predicate

sealed class EvolvedAccessorsImpl<T>(
    list: List<T>,
    parent: Change<*>?,
) : EvolvedCollectingImpl<T>(list, parent),
    EvolvedAccessors<T> {

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