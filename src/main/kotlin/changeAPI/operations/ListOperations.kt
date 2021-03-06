package changeAPI.operations

import changeAPI.changes.Change
import java.util.function.Predicate

interface ListOperations<T>{
    // add
    fun add(element: T): Change<T>
    fun addAll(vararg elements: T): Change<T>
    fun addAll(list: List<T>): Change<T>
    // remove
    fun removeAll(comparator: Comparator<T>, vararg elements: T): Change<T>
    fun removeAll(comparator: Comparator<T>, list: List<T>): Change<T>
    fun removeFirst(comparator: Comparator<T>, vararg elements: T): Change<T>
    fun removeFirst(comparator: Comparator<T>, list: List<T>): Change<T>
    fun removeLast(comparator: Comparator<T>, vararg elements: T): Change<T>
    fun removeLast(comparator: Comparator<T>, list: List<T>): Change<T>
    fun removeAt(vararg indexes: Int): Change<T>
    fun removeAt(list: List<Int>): Change<T>
    fun removeIf(filter: Predicate<T>): Change<T>
    fun removeIf(filter: (T) -> Boolean): Change<T>
    // retain
    fun retainAll(comparator: Comparator<T>, vararg elements: T): Change<T>
    fun retainAll(comparator: Comparator<T>, list: List<T>): Change<T>
    fun retainFirst(comparator: Comparator<T>, vararg elements: T): Change<T>
    fun retainFirst(comparator: Comparator<T>, list: List<T>): Change<T>
    fun retainLast(comparator: Comparator<T>, vararg elements: T): Change<T>
    fun retainLast(comparator: Comparator<T>, list: List<T>): Change<T>
    fun retainAt(vararg indexes: Int): Change<T>
    fun retainAt(list: List<Int>): Change<T>
    fun retainIf(filter: Predicate<T>): Change<T>
    fun retainIf(filter: (T) -> Boolean): Change<T>
    // setting
    fun setAll(comparator: Comparator<T>, vararg pairs: Pair<T, T>): Change<T>
    fun setAll(comparator: Comparator<T>, replacingMap: Map<T, T>): Change<T>
    fun setFirst(comparator: Comparator<T>, vararg pairs: Pair<T, T>): Change<T>
    fun setFirst(comparator: Comparator<T>, replacingMap: Map<T, T>): Change<T>
    fun setLast(comparator: Comparator<T>, vararg pairs: Pair<T, T>): Change<T>
    fun setLast(comparator: Comparator<T>, replacingMap: Map<T, T>): Change<T>
    fun setAt(vararg pairs: Pair<Int, T>): Change<T>
    fun setAt(indexMap: Map<Int, T>): Change<T>
    // ordering
    fun sorted(comparator: Comparator<T>): Change<T>
    fun unique(comparator: Comparator<T>): Change<T>
    // functions
    fun forEach(function: (T) -> T): Change<T>
}
