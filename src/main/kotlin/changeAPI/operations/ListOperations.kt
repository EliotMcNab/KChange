package changeAPI.operations

import changeAPI.changes.ListChange
import changeAPI.changes.PrimitiveChange
import java.util.function.Predicate

interface ListOperations<T>{
    // add
    fun add(element: T): ListChange<T>
    fun addAll(vararg elements: T): ListChange<T>
    fun addAll(list: List<T>): ListChange<T>
    // remove
    fun removeAll(comparator: Comparator<T>, vararg elements: T): ListChange<T>
    fun removeAll(comparator: Comparator<T>, list: List<T>): ListChange<T>
    fun removeFirst(comparator: Comparator<T>, vararg elements: T): ListChange<T>
    fun removeFirst(comparator: Comparator<T>, list: List<T>): ListChange<T>
    fun removeLast(comparator: Comparator<T>, vararg elements: T): ListChange<T>
    fun removeLast(comparator: Comparator<T>, list: List<T>): ListChange<T>
    fun removeAt(vararg indexes: Int): ListChange<T>
    fun removeAt(list: List<Int>): ListChange<T>
    fun removeIf(filter: Predicate<T>): ListChange<T>
    fun removeIf(filter: (T) -> Boolean): ListChange<T>
    // retain
    fun retainAll(comparator: Comparator<T>, vararg elements: T): ListChange<T>
    fun retainAll(comparator: Comparator<T>, list: List<T>): ListChange<T>
    fun retainFirst(comparator: Comparator<T>, vararg elements: T): ListChange<T>
    fun retainFirst(comparator: Comparator<T>, list: List<T>): ListChange<T>
    fun retainLast(comparator: Comparator<T>, vararg elements: T): ListChange<T>
    fun retainLast(comparator: Comparator<T>, list: List<T>): ListChange<T>
    fun retainAt(vararg indexes: Int): ListChange<T>
    fun retainAt(list: List<Int>): ListChange<T>
    fun retainIf(filter: Predicate<T>): ListChange<T>
    fun retainIf(filter: (T) -> Boolean): ListChange<T>
    // setting
    fun setAll(comparator: Comparator<T>, vararg pairs: Pair<T, T>): ListChange<T>
    fun setAll(comparator: Comparator<T>, replacingMap: Map<T, T>): ListChange<T>
    fun setFirst(comparator: Comparator<T>, vararg pairs: Pair<T, T>): ListChange<T>
    fun setFirst(comparator: Comparator<T>, replacingMap: Map<T, T>): ListChange<T>
    fun setLast(comparator: Comparator<T>, vararg pairs: Pair<T, T>): ListChange<T>
    fun setLast(comparator: Comparator<T>, replacingMap: Map<T, T>): ListChange<T>
    fun setAt(vararg pairs: Pair<Int, T>): ListChange<T>
    fun setAt(indexMap: Map<Int, T>): ListChange<T>
    // ordering
    fun sorted(comparator: Comparator<T>): ListChange<T>
    fun unique(comparator: Comparator<T>): ListChange<T>
}
