package changeAPI.changes

import changeAPI.*
import changeAPI.information.CharOperator
import changeAPI.information.IntOperator
import changeAPI.information.Operator
import changeAPI.operations.PrimitiveOperations
import comparisons.CharComparator
import comparisons.IntComparator
import java.util.function.Predicate

sealed class PrimitiveOperationsImpl<T>(
    list: List<T>,
    parent: Change<*>?,
    private val comparator: Comparator<T>,
    private val operator: Operator<T>
) : MappingImpl<T>(list, parent), PrimitiveOperations<T> {
    // ADDING
    override fun add(element: T): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, Add(listOf(element), this))

    override fun addAll(vararg elements: T): PrimitiveChange<T> =
        addAll(elements.toList())

    override fun addAll(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, Add(list, this))

    // REMOVING
    override fun removeAll(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        removeAll(comparator, elements.toList())

    override fun removeAll(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveAll(list, comparator, this))

    override fun removeAll(vararg elements: T): PrimitiveChange<T> =
        removeAll(elements.toList())

    override fun removeAll(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveAll(list, comparator, this))

    override fun removeFirst(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        removeFirst(comparator, elements.toList())

    override fun removeFirst(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveFirst(list, comparator, this))

    override fun removeFirst(vararg elements: T): PrimitiveChange<T> =
        removeFirst(elements.toList())

    override fun removeFirst(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveFirst(list, comparator, this))

    override fun removeLast(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        removeLast(comparator, elements.toList())

    override fun removeLast(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveLast(list, comparator, this))

    override fun removeLast(vararg elements: T): PrimitiveChange<T> =
        removeLast(elements.toList())

    override fun removeLast(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveLast(list, comparator, this))

    override fun removeAt(vararg indexes: Int): PrimitiveChange<T> =
        removeAt(indexes.toList())

    override fun removeAt(list: List<Int>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveAt(list, this))

    override fun removeIf(filter: Predicate<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveIf(filter, this))

    override fun removeIf(filter: (T) -> Boolean): PrimitiveChange<T> =
        removeIf(Predicate(filter))

    // RETAINING
    override fun retainAll(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        retainAll(comparator, elements.toList())

    override fun retainAll(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainAll(list, comparator, this))

    override fun retainAll(vararg elements: T): PrimitiveChange<T> =
        retainAll(elements.toList())

    override fun retainAll(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainAll(list, comparator, this))

    override fun retainFirst(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        retainFirst(comparator, elements.toList())

    override fun retainFirst(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainFirst(list, comparator, this))

    override fun retainFirst(vararg elements: T): PrimitiveChange<T> =
        retainFirst(elements.toList())

    override fun retainFirst(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainFirst(list, comparator, this))

    override fun retainLast(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        retainLast(comparator, elements.toList())

    override fun retainLast(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainLast(list, comparator, this))

    override fun retainLast(vararg elements: T): PrimitiveChange<T> =
        retainLast(elements.toList())

    override fun retainLast(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainLast(list, comparator, this))

    override fun retainAt(vararg indexes: Int): PrimitiveChange<T> =
        retainAt(indexes.toList())

    override fun retainAt(list: List<Int>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainAt(list, this))

    override fun retainIf(filter: Predicate<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainIf(filter, this))

    override fun retainIf(filter: (T) -> Boolean): PrimitiveChange<T> =
        retainIf(Predicate(filter))

    // SETTING
    override fun setAll(comparator: Comparator<T>, vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setAll(comparator, mapOf(*pairs))

    override fun setAll(comparator: Comparator<T>, replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, SetAll(replacingMap, comparator, this))

    override fun setAll(vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setAll(mapOf(*pairs))

    override fun setAll(replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, SetAll(replacingMap, comparator, this))

    override fun setFirst(comparator: Comparator<T>, vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setFirst(comparator, mapOf(*pairs))

    override fun setFirst(comparator: Comparator<T>, replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, SetFirst(replacingMap, comparator, this))

    override fun setFirst(vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setFirst(mapOf(*pairs))

    override fun setFirst(replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, SetFirst(replacingMap, comparator, this))

    override fun setLast(comparator: Comparator<T>, vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setLast(comparator, mapOf(*pairs))

    override fun setLast(comparator: Comparator<T>, replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, SetLast(replacingMap, comparator, this))

    override fun setLast(vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setLast(mapOf(*pairs))

    override fun setLast(replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, SetLast(replacingMap, comparator, this))

    override fun setAt(vararg pairs: Pair<Int, T>): PrimitiveChange<T> =
        setAt(mapOf(*pairs))

    override fun setAt(indexMap: Map<Int, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, SetAt(indexMap, this))

    // ORDERING
    override fun sorted(comparator: Comparator<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, Sorted(comparator, this))

    override fun sorted(): PrimitiveChange<T> =
        sorted(comparator)

    override fun unique(comparator: Comparator<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, Unique(comparator, this))

    override fun unique(): PrimitiveChange<T> =
        unique(comparator)
}