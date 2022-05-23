package changeAPI.operations

import changeAPI.changes.ListChange
import changeAPI.changes.PrimitiveChange
import java.util.function.Predicate

sealed interface PrimitiveRedefinitions<T> : ListOperations<T> {
    // add
    override fun add(element: T): PrimitiveChange<T>
    override fun addAll(vararg elements: T): PrimitiveChange<T>
    override fun addAll(list: List<T>): PrimitiveChange<T>
    // remove
    override fun removeAll(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T>
    override fun removeAll(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T>
    override fun removeFirst(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T>
    override fun removeFirst(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T>
    override fun removeLast(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T>
    override fun removeLast(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T>
    override fun removeAt(vararg indexes: Int): PrimitiveChange<T>
    override fun removeAt(list: List<Int>): PrimitiveChange<T>
    override fun removeIf(filter: Predicate<T>): PrimitiveChange<T>
    override fun removeIf(filter: (T) -> Boolean): PrimitiveChange<T>
    // retain
    override fun retainAll(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T>
    override fun retainAll(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T>
    override fun retainFirst(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T>
    override fun retainFirst(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T>
    override fun retainLast(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T>
    override fun retainLast(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T>
    override fun retainAt(vararg indexes: Int): PrimitiveChange<T>
    override fun retainAt(list: List<Int>): PrimitiveChange<T>
    override fun retainIf(filter: Predicate<T>): PrimitiveChange<T>
    override fun retainIf(filter: (T) -> Boolean): PrimitiveChange<T>
    // setting
    override fun setAll(comparator: Comparator<T>, vararg pairs: Pair<T, T>): PrimitiveChange<T>
    override fun setAll(comparator: Comparator<T>, replacingMap: Map<T, T>): PrimitiveChange<T>
    override fun setFirst(comparator: Comparator<T>, vararg pairs: Pair<T, T>): PrimitiveChange<T>
    override fun setFirst(comparator: Comparator<T>, replacingMap: Map<T, T>): PrimitiveChange<T>
    override fun setLast(comparator: Comparator<T>, vararg pairs: Pair<T, T>): PrimitiveChange<T>
    override fun setLast(comparator: Comparator<T>, replacingMap: Map<T, T>): PrimitiveChange<T>
    override fun setAt(vararg pairs: Pair<Int, T>): PrimitiveChange<T>
    override fun setAt(indexMap: Map<Int, T>): PrimitiveChange<T>
    // ordering
    override fun sorted(comparator: Comparator<T>): PrimitiveChange<T>
    override fun unique(comparator: Comparator<T>): PrimitiveChange<T>

}