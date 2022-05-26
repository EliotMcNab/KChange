package changeAPI.changes

import changeAPI.*
import changeAPI.operations.EvolvedOperations
import java.util.function.Predicate

sealed class EvolvedOperationsImpl<T>(
    list: List<T>,
    parent: Change<*>?
) : TransformImpl<T>(list, parent), EvolvedOperations<T> {
    // ADDING
    override fun add(element: T) : EvolvedChange<T> =
        Add(listOf(element), this)

    override fun addAll(vararg elements: T): EvolvedChange<T> =
        addAll(elements.toList())

    override fun addAll(list: List<T>): EvolvedChange<T> =
        Add(list, this)

    // REMOVING
    override fun removeAll(comparator: Comparator<T>, vararg elements: T): EvolvedChange<T> =
        removeAll(comparator, elements.toList())

    override fun removeAll(comparator: Comparator<T>, list: List<T>): EvolvedChange<T> =
        RemoveAll(list, comparator, this)

    override fun removeFirst(comparator: Comparator<T>, vararg elements: T): EvolvedChange<T> =
        removeFirst(comparator, elements.toList())

    override fun removeFirst(comparator: Comparator<T>, list: List<T>): EvolvedChange<T> =
        RemoveFirst(list, comparator, this)

    override fun removeLast(comparator: Comparator<T>, vararg elements: T): EvolvedChange<T> =
        removeLast(comparator, elements.toList())

    override fun removeLast(comparator: Comparator<T>, list: List<T>): EvolvedChange<T> =
        RemoveLast(list, comparator, this)

    override fun removeAt(vararg indexes: Int): EvolvedChange<T> =
        removeAt(indexes.toList())

    override fun removeAt(list: List<Int>): EvolvedChange<T> =
        RemoveAt(list, this)

    override fun removeIf(filter: Predicate<T>): EvolvedChange<T> =
        RemoveIf(filter, this)

    override fun removeIf(filter: (T) -> Boolean): EvolvedChange<T> =
        removeIf(Predicate(filter))

    // RETAINING
    override fun retainAll(comparator: Comparator<T>, vararg elements: T): EvolvedChange<T> =
        retainAll(comparator, elements.toList())

    override fun retainAll(comparator: Comparator<T>, list: List<T>): EvolvedChange<T> =
        RetainAll(list, comparator, this)
    override fun retainFirst(comparator: Comparator<T>, vararg elements: T): EvolvedChange<T> =
        retainFirst(comparator, elements.toList())

    override fun retainFirst(comparator: Comparator<T>, list: List<T>): EvolvedChange<T> =
        RetainFirst(list, comparator, this)

    override fun retainLast(comparator: Comparator<T>, vararg elements: T): EvolvedChange<T> =
        retainLast(comparator, elements.toList())

    override fun retainLast(comparator: Comparator<T>, list: List<T>): EvolvedChange<T> =
        RetainLast(list, comparator, this)

    override fun retainAt(vararg indexes: Int): EvolvedChange<T> =
        retainAt(indexes.toList())

    override fun retainAt(list: List<Int>): EvolvedChange<T> =
        RetainAt(list, this)

    override fun retainIf(filter: Predicate<T>): EvolvedChange<T> =
        RetainIf(filter, this)

    override fun retainIf(filter: (T) -> Boolean): EvolvedChange<T> =
        retainIf(Predicate(filter))

    // SETTING
    override fun setAll(comparator: Comparator<T>, vararg pairs: Pair<T, T>): EvolvedChange<T> =
        setAll(comparator, mapOf(*pairs))

    override fun setAll(comparator: Comparator<T>, replacingMap: Map<T, T>): EvolvedChange<T> =
        SetAll(replacingMap, comparator, this)

    override fun setFirst(comparator: Comparator<T>, vararg pairs: Pair<T, T>): EvolvedChange<T> =
        setFirst(comparator, mapOf(*pairs))

    override fun setFirst(comparator: Comparator<T>, replacingMap: Map<T, T>): EvolvedChange<T> =
        SetFirst(replacingMap, comparator, this)

    override fun setLast(comparator: Comparator<T>, vararg pairs: Pair<T, T>): EvolvedChange<T> =
        setLast(comparator, mapOf(*pairs))

    override fun setLast(comparator: Comparator<T>, replacingMap: Map<T, T>): EvolvedChange<T> =
        SetLast(replacingMap, comparator, this)

    override fun setAt(vararg pairs: Pair<Int, T>): EvolvedChange<T> =
        setAt(mapOf(*pairs))
    override fun setAt(indexMap: Map<Int, T>): EvolvedChange<T> =
        SetAt(indexMap, this)

    // ORDERING
    override fun sorted(comparator: Comparator<T>): EvolvedChange<T> =
        Sorted(comparator, this)

    override fun unique(comparator: Comparator<T>): EvolvedChange<T> =
        Unique(comparator, this)

    // FUNCTIONS
    override fun forEach(function: (T) -> T): EvolvedChange<T> =
        ForEach(function, this)
}
